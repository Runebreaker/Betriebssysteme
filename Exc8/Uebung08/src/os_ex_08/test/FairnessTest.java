package os_ex_08.test;

import os_ex_08.Semaphore;
import os_ex_08_3.semaphore.FairSemaphore;
import os_ex_08_3.semaphore.UnfairSemaphore;

/** Test of the fairness of a semaphore.
 */
public class FairnessTest {
    /** Task used to check the fairness of a semaphore.

	This task sleeps an interval to force other tasks to wait
	on the semaphore, causing multiple threads to wait on the
	semaphore. At the same time, the task checks if it is allowed
	to enter the critical section at the right time, that is whether
	all tasks enter the critical section in the correct order.
    */
    protected class Task implements Runnable {
	/** Sequential id.
	 */
	protected int id;

	/** Construct the task with the sequential id.
	 */
	public Task(int i) {
	    id = i;
	}

	/** Check for correct execution order, indicating fairness
	    of the used semaphore.
	*/
	public void run() {
	    try {
		Thread.sleep(id * 10 + 10);
		semaphore.acquire();
		if (counter == id) {
		    ++counter;
		}
		Thread.sleep(30);
		semaphore.release();
	    } catch (InterruptedException ie) {
		counter = -1; // Interrupted, so fail hard
	    }
	}
    }

    /** Counter that is incremented by the running tasks and used to
	check whether they're run in correct order.
    */
    protected int counter = 0;

    /** The used semaphore. This class assumes that it's initial set
	to have exactly 1 permit.
    */
    protected Semaphore semaphore;

    /** Run the tasks once, checking for their correct execution order.
     */
    public boolean fairRun() {
	counter = 0;
	Thread[] threads = new Thread[13];
	for (int i = 0; i < threads.length; ++i) {
	    threads[i] = new Thread(new Task(i));
	}
	for (Thread t : threads) {
	    t.start();
	}
	for (Thread t : threads) {
	    try {
		t.join();
	    } catch (InterruptedException ie) {
		// Unhandled on purpose
	    }
	}
	return (counter == threads.length);
    }

    /** Perform the test by repeatedly testing whether the semaphore could
	allow us to run a {@link #fairRun()}.
    */
    public boolean perform() {
	for (int i = 0; i < 13; ++i) {
	    if (! fairRun()) {
		return false;
	    }
	}
	return true;
    }

    /** Construct a test instance, using the given semaphore as locking
	primitive.
    */
    public FairnessTest(Semaphore s) {
	semaphore = s;
    }

    /** Quick'n'dirty executable check of both the fair and the unfair
	semaphores. Won't compile if you don't have a FairSemaphore.
    */
    public static void main(String[] args) {
	FairnessTest uf = new FairnessTest(new UnfairSemaphore(1));
	System.out.println("UnfairSemaphore seems to be " +
			   (uf.perform() ? "fair" : "unfair"));
	FairnessTest f = new FairnessTest(new FairSemaphore(1));
	System.out.println("FairSemaphore seems to be " +
			   (f.perform() ? "fair" : "unfair"));
    }
}
