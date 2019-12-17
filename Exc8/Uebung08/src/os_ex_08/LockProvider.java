package os_ex_08;

import java.util.Queue;
import java.util.LinkedList;

/** Provides {@linkplain Lock locks} and ways to manage and supervise them.

    Objects of this class provide locks, which are monitored for
    various events on them, enabling the passed {@link Listener} to log
    or even react to these events.
*/
public class LockProvider {
    /** Listen to events on locks.

	Implementing this interface is the intended way to enable a
	class to listen (and react) to events happening on the
	{@linkplain Lock locks} of a {@link LockProvider}.
    */
    public interface Listener {
	/** A {@link Lock} got locked.

	    Called when a lock got locked. This is running on the
	    thread that now holds that lock.
	*/
	void onLocked(Lock lock);

	/** A {@link Lock} that needs to be waited for.

	    Called when a lock has been tried to lock, but the current
	    thread needs to wait because the lock is held by another
	    thread.
	*/
	void onWaitingFor(Lock lock);

	/** A thread waiting for a lock got interrupted.

	    The current thread has been waiting on the lock, but has
	    been interrupted doing so. Thus, the thread will not
	    acquire the lock unless trying to lock it again.
	*/
	void onFailedWaitingFor(Lock lock);

	/** A {@link Lock} got unlocked.

	    Called when a lock got unlocked. This is called
	    right after eventually waiting threads are notified. It's
	    called on the same thread that unlocked the lock.
	*/
	void onUnlocked(Lock lock);

	/** A new {@link Lock} has been provided by the {@link LockProvider}.

	    Called when a new lock is provided to some caller. Mainly
	    intended to be able to keep information of available locks.
	*/
	void onProvided(Lock lock);
    }

    /** A flag to ease communication in the {@link L lock} between the
	unlocking thread and possibly waiting threads.
    */
    protected static class Flag {
	/** Internal boolean indicating whether the flag is raised
	    or not.

	    This boolean has to be wrapped into the {@link Flag} class
	    in order to be modifyable but have reference semantics.
	*/
	public boolean raised = false;
    }
    
    /** Lock which can be locked and unlocked, obviously.

	These are the locks that are provided by the {@link LockProvider}
	and are monitored by the {@link Listener}. It's a fair lock.
    */
    protected class L implements Lock {
	/** Queue of notifications, which each notifies a thread that
	    is currently waiting to lock this lock.
	*/
	protected Queue<Flag> notifications =
	    new LinkedList<Flag>();

	/** State of the lock.
	 */
	protected boolean locked = false;

	// Implemented
	public void lock() throws InterruptedException {
	    Flag notification;
	    synchronized (notifications) {
		if (locked) {
		    notification = new Flag();
		    notifications.add(notification);
		} else {
		    locked = true;
		    listener.onLocked(this);
		    return;
		}
	    }
	    // Only arrive here after adding ourself to the notifications
	    // queue.
	    synchronized (notification) {
		if (! notification.raised) {
		    listener.onWaitingFor(this);
		    try {
			notification.wait();
		    } catch (InterruptedException ie) {
			notification.raised = true;
			listener.onFailedWaitingFor(this);
			throw ie;
		    }
		}
		listener.onLocked(this);
	    }
	}

	// Implemented
	public void unlock() {
	    synchronized (notifications) {
		while (! notifications.isEmpty()) {
		    Flag notification = notifications.remove();
		    synchronized (notification) {
			if (! notification.raised) {
			    notification.raised = true;
			    notification.notify();
			    listener.onUnlocked(this);
			    return;
			}
		    }
		}
		locked = false;
		listener.onUnlocked(this);
	    }
	}
    }

    /** The {@link Listener} which is notified of any events.
     */
    protected Listener listener;

    /** Construct a new provider, using the given {@link Listener}.
     */
    public LockProvider(Listener l) {
	listener = l;
    }

    /** Provide a new lock.

	Call this method to obtain a new lock from the
	{@link LockProvider}. The {@link Listener} is notified about
	this event.
    */
    public Lock provide() {
	Lock lock = this.new L();
	listener.onProvided(lock);
	return lock;
    }
}
