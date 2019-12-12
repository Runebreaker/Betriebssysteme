package Task2;

/**
 * A counter whose value can be read and written.
 * 
 * Encapsulates a single integer value and provides a point to insert code to be
 * executed on read or write access to that value, in case that is ever needed.
 */
class Counter {
	/**
	 * Internal value. Initially set to 0.
	 */
	protected int count = 0;

	/**
	 * Sets the internal value to the new value.
	 */
	public void set(int newCount) {
		count = newCount;
	}

	/**
	 * Retrieve value.
	 */
	public int get() {
		return count;
	}
}
