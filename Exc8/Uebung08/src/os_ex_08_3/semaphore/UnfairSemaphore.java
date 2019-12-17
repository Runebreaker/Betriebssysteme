package os_ex_08_3.semaphore;

import os_ex_08.Semaphore;

/** Simple, unfair counting semaphore.

    This class implements a simple and unfair counting semaphore.
*/
public class UnfairSemaphore implements Semaphore {
    /** Count of available permits.
     */
    protected int count;

    /** Object used to communicate with waiting threads.

	A distinct object is used herefore (and not the semaphore itself)
	to keep any signals from notify() behind the abstraction.
     */
    protected Object wire = new Object();

    /** Construct a semaphore with the given initial count.

	Sets the initial count of the newly constructed semaphore to the
	given value, which may be negative.
    */
    public UnfairSemaphore(int initial) {
	count = initial;
    }

    /** Construct a semaphore with 1 initial permit.
     */
    public UnfairSemaphore() {
	this(1);
    }

    // Implementation
    public void acquire() throws InterruptedException {
	synchronized (wire) {
	    while (count <= 0) {
		wire.wait();
	    }
	    --count;
	}
    }

    // Implementation
    public void release() {
	synchronized (wire) {
	    ++count;
	    if (count > 0) {
		wire.notifyAll();
	    }
	}
    }
}
