package os_ex_08_1.prod_con;

import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	ReentrantLock lock = new ReentrantLock();

	public volatile boolean isLocked = true;

	protected String content = null;
	protected int amount = 1;

	public void put(String data) {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		lock.lock();
		isLocked = false;
		if(data.equals(content)) //If: part of b)
		{
			amount++;
		}
		else
		{
			content = data;
		}
		lock.unlock();
	}

	public String get() {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		lock.lock();
		String data = content;
		if(amount > 1) //If: part of b)
		{
			amount--;
		}
		else
		{
			content = null;
		}
		lock.unlock();
		return data;
	}
}
