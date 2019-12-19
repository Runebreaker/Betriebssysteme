package os_ex_08_3.semaphore;

import os_ex_08.Semaphore;

public class UnfairSemaphore implements Semaphore {
    protected int count;
    protected Object wire = new Object();

    public UnfairSemaphore(int initial) {
	count = initial;
    }

    public UnfairSemaphore() {
	this(1);
    }

    // Implementation
    public void acquire() throws InterruptedException {
	synchronized (wire) {
	    while (count <= 0) {
		wire.wait();
	    }
	    --count;
	}
    }

    // Implementation
    public void release() {
	synchronized (wire) {
	    ++count;
	    if (count > 0) {
		wire.notifyAll();
	    }
	}
    }
}
