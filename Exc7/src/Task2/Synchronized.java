package Task2;

/**
 * Full synchronized increment of the counter.
 * 
 * This strategy extends the Task2.Unsynchronized strategy by using full
 * synchronisation of the whole process of reading, incrementing and writing the
 * counter.
 */
class Synchronized extends Unsynchronized {
	@Override
	public void increment(Counter counter) {
		synchronized (counter) {
			super.increment(counter);
		}
	}
}
