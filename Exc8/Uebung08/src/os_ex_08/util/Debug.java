package os_ex_08.util;

/** Provide some tools for debugging / analysing the code.
 */
public class Debug {
    /** Say a message, prefixed by time and thread name.
     */
    public static void say(String message) {
        Thread thread = Thread.currentThread();
        long time = System.nanoTime();
        System.out.println(time + " [" + thread.getName() + "] " + message);
    }
}
