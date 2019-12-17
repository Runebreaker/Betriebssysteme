package os_ex_08;

/** A Semaphore.

    This interface is intended as abstraction of the concept of a
    semaphore, so that other code can ignore the differences between
    actual implementations.
*/
public interface Semaphore {

    /** Acquire a permit from this semaphore, blocking if neccessary.
     */
    void acquire() throws InterruptedException;

    /** Release a permit to this semaphore, possibly unblocking blocked
	threads.
    */
    void release();
}
