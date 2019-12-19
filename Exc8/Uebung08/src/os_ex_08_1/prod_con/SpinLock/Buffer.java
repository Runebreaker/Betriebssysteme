package os_ex_08_1.prod_con.SpinLock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	ReentrantLock lock = new ReentrantLock();

	public AtomicBoolean hasProduct = new AtomicBoolean(false);

	protected String content = null;
	protected int amount = 1;

	public void put(String data) {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		lock.tryLock();
		hasProduct.set(true);
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
		while (!hasProduct.get());
		lock.tryLock();
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
