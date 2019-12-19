package os_ex_08_3.semaphore;

import os_ex_08.Semaphore;

import java.util.ArrayDeque;

public class FairSemaphore implements Semaphore {
	private ArrayDeque<Long> waitQueue = new ArrayDeque<>();
	protected final int capacity;
	protected int count;

	public FairSemaphore(int initial)
	{
		capacity = initial;
	}

	public synchronized void acquire() throws InterruptedException
	{
		waitQueue.addLast(Thread.currentThread().getId());

		while(!(count < capacity && Thread.currentThread().getId() == waitQueue.getFirst())) //Checks if resource is available and current thread is eligible to get permit (fairness)
		{																			  		 //Basically, every Thread is paused until needed
			wait();
		}

		waitQueue.removeFirst();

		count++;
	}

	public synchronized void release()
	{
		if(count > 0)
		{
			count--;
			notifyAll();
		}
	}
}
