package Task2;

public class Util {
    public static void waitForEnter() {
	System.out.println("Press ENTER to continue!");
	try {
	    System.in.read();
	} catch (Exception e) {
	    // Not handled on purpose!
	}
    }
}
