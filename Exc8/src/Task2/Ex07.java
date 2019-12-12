package Task2;

public class Ex07 {
	/**
	 * Run the given task on some threads.
	 * 
	 * Runs the given task - defined by an instance of class java.lang.Runnable - on
	 * the specified number of threads. Note that only a single instance of the task
	 * is used, thus any state of that task is shared across the threads.
	 */
	public static void runOnThreads(Runnable task, int count) {
		Thread[] threads = new Thread[count];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(task);
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException ie) {
				// Intentionally left unhandled
			}
		}
	}

	public static void main(String[] args) {
		final int repetitions = 100;
		final int numberOfThreads = 3;

		Counter counter = new Counter();
		System.out.println("Running counter unsynchronized " + "(#Threads = " + numberOfThreads + ")");
		Util.waitForEnter();
		runOnThreads(new Counting(new Unsynchronized(), counter, repetitions), numberOfThreads);
		System.out.println("Result = " + counter.get());

		counter.set(0); // Reset counter
		System.out.println("Running counter synchronized " + "(#Threads = " + numberOfThreads + ")");
		Util.waitForEnter();
		runOnThreads(new Counting(new Synchronized(), counter, repetitions), numberOfThreads);
		System.out.println("Result = " + counter.get());

		Util.waitForEnter();
	}
}
