package Task2;

class Counting implements Runnable {
	/**
	 * Abstracts the way the actual operation is applied to the counter.
	 * 
	 * This interface allows parameterisation of the Task2.Counting class on the exact way
	 * the actual operation - incrementing a value - is applied to the given
	 * counter. Implementations are supposed to use rawIncrement to perform the
	 * actual operation, passing along the Task2.Counter given as parameter to the
	 * interface method.
	 */
	public interface Strategy {
		/**
		 * Perform increment of given Task2.Counter.
		 */
		public void increment(Counter counter);
	}

	/**
	 * Actual operation to be performed, incrementing some value.
	 * 
	 * This is the actual operation that is to be performed. The passed value is
	 * incremented by one and returned after some delay.
	 * 
	 * @param value
	 *            The value to increment.
	 * @return Passed value plus 1.
	 */
	public static int rawIncrement(int value) {
		try {
			Thread.sleep(50 + (int) (Math.random() * 50));
		} catch (InterruptedException ie) {
			// Intentionally unhandled
		}
		return value + 1;
	}

	/**
	 * Task2.Counter to increment.
	 */
	protected Counter counter;

	/**
	 * Total number of increments to do.
	 */
	protected int repetitions;

	/**
	 * Strategy of how to perform the increments.
	 */
	protected Strategy strategy;

	/**
	 * Repeatedly increment the given counter, using to given strategy.
	 */
	public void run() {
		for (int localCount = 0; localCount < repetitions; localCount++) {
			strategy.increment(counter);
		}
	}

	Counting(Strategy s, Counter c, int r) {
		strategy = s;
		counter = c;
		repetitions = r;
	}
}
