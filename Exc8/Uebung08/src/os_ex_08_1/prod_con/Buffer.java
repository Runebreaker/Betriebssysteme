package os_ex_08_1.prod_con;

import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	ReentrantLock lock = new ReentrantLock();

	public volatile boolean isLocked = true;

	protected String content = null;
	protected int amount;

	public void put(String data) {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		lock.lock();
		isLocked = false;
		if(data.equals(content))
		{
			amount++;
		}
		else
		{
			content = data;
			amount = 1;
		}

		lock.unlock();
	}

	public String get() {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		lock.lock();
		if(amount > 1)
		{
			amount--;
		}
		else
		{
			content = null;
		}
		String data = content;
		lock.unlock();
		return data;
	}
}
