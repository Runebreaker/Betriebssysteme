package os_ex_08;

/** Abstract concept of a lock.
 */
public interface Lock {
    /** A lock may be locked, which is a blocking action.

	@throws InterruptedException Thrown when interrupted while waiting
	to acquire the lock.
     */
    void lock() throws InterruptedException;

    /** Unlocking of a previously locked lock.
     */
    void unlock();
}
