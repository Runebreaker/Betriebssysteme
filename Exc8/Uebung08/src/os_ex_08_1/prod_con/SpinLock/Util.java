package os_ex_08_1.prod_con.SpinLock;

public class Util {
	public static void say(String message) {
		System.out.println(Thread.currentThread().getName() + ": " + message);
	}
}
