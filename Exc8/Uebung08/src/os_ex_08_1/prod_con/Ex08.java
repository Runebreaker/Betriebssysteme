package os_ex_08_1.prod_con;

public class Ex08 {
	protected static Thread createProducer(String name, Buffer buffer, String data, int repeat) {
		Runnable task = () -> {
			for (int i = 0; i < repeat; ++i) {
				buffer.put(data);
			}
		};
		return new Thread(task, name);
	}

	protected static Thread createConsumer(String name, Buffer buffer, int repeat) {
		Runnable task = () -> {
			for (int i = 0; i < repeat; ++i) {
				String data = buffer.get();
				System.out.println(name + " consumed 1 " + data);
			}
		};
		return new Thread(task, name);
	}

	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer();
		Thread producer = createProducer("P", buffer, "pizza", 5);
		Thread consumer = createConsumer("C", buffer, 5);
		System.out.println("Starting ...");

		producer.start();
		consumer.start();

		System.out.println("All up and running, waiting for finish ...");
		producer.join();
		consumer.join();
		System.out.println("Done, Bye!");
	}
}
