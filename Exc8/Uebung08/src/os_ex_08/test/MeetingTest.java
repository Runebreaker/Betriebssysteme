package os_ex_08.test;

import java.util.concurrent.atomic.AtomicInteger;

import os_ex_08.Arrival;
import os_ex_08.Meeting;
import os_ex_08_4.meeting.ForN;
import os_ex_08_4.meeting.ForTwo;

import java.util.concurrent.atomic.AtomicBoolean;

/** Tests whether threads actually meet before continuing to go their
    own way.
*/
public class MeetingTest {
    /** Task done by each thread.
     */
    protected class Task implements Runnable {
	/** The id of the current thread, used to have a different
	    sleep time for each thread.
	*/
	protected int id;

	/** The arrival which is going to be announced after completing
	    the sleep and incrementing the shared counter.
	*/
	protected Arrival arrival;

	/** Setup a new task, with given id and arrival notificator.
	 */
	public Task(int i, Arrival a) {
	    id = i;
	    arrival = a;
	}

	/** Sleep, increment, announce. Then check whether all threads
	    have incremented the counter.
	*/
	public void run() {
	    try {
		Thread.sleep(id * 10);
		counter.incrementAndGet();
		arrival.announce();
		passed.compareAndSet(true,
				 counter.intValue() == numberOfTasks);
	    } catch (InterruptedException ie) {
		throw new RuntimeException("Don't interrupt tests please",
					   ie);
	    }
	}
    }

    /** Number of concurrent task to run.
     */
    protected final int numberOfTasks;

    /** The counter that is incremented by the tasks.
     */
    protected AtomicInteger counter;

    /** Shared flag indicating if any task found a failure.
     */
    protected AtomicBoolean passed;

    /** The meeting that is being tested.

	This meeting class supplies the tasks with
	{@link os_ex_09.Arrival} objects which are used to signal
	arrival at the common meeting point.
    */
    protected Meeting meeting;

    /** Construct a new test for the given meeting with the given
	number of tasks.
    */
    public MeetingTest(Meeting m, int n) {
	meeting = m;
	numberOfTasks = n;
    }

    /** Perform the test.
     */
    public boolean perform() {
	counter = new AtomicInteger(0);
	passed = new AtomicBoolean(true);
	Thread[] threads = new Thread[numberOfTasks];
	Arrival[] arrivals = meeting.arrange();
	for (int i = 0; i < threads.length; ++i) {
	    Runnable task = new Task(i, arrivals[i]);
	    threads[i] = new Thread(task);
	}
	for (Thread t : threads) {
	    t.start();
	}
	for (Thread t : threads) {
	    try {
		t.join();
	    } catch (InterruptedException ie) {
		// Intentionally unhandled
	    }
	}
	return passed.get();
    }

    /** Quick'n'dirty way to run the test.
     */
    public static void main(String[] args) {
	MeetingTest two = new MeetingTest(new ForTwo(), 2);
	System.out.println("ForTwo seems to be " +
			   (two.perform() ? "correct" : "incorrect"));
	MeetingTest n = new MeetingTest(new ForN(1234), 1234);
	System.out.println("ForN seems to be " +
			   (n.perform() ? "correct" : "incorrect"));
    }
}
