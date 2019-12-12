package Task2;

/**
 * Incrementing the counter without any synchronisation.
 * 
 * This strategy implements the naive version: Just ignoring any race conditions
 * and accessing the counter without any means of synchronisation.
 */
class Unsynchronized implements Counting.Strategy {
	public void increment(Counter counter) {
		int current = counter.get();
		current = Counting.rawIncrement(current);
		counter.set(current);
	}
}
