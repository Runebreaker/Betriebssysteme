package os_ex_08;

/** Concept of announcing ones arrival at a {@link Meeting}.
 */
public interface Arrival {
    /** Announce that one has arrived at the arranged meeting.
     */
    void announce() throws InterruptedException;
}
