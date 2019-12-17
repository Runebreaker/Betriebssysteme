package os_ex_08_1.prod_con;

public class Buffer {
	protected String content = null;

	public void put(String data) {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		content = data;
	}

	public String get() {
		// Make me thread safe and synchronize me (this is intended to
		// be a blocking call)
		String data = content;
		content = null;
		return data;
	}
}
