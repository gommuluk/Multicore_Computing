import java.util.concurrent.*;

public class ex1 {

	public static void main(String[] args) {
		
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

		System.out.println("Producer Thread Start!");
		Producer p = new Producer(queue);
		p.start();
		
		System.out.println("Consumer Thread Start!");
		Consumer c = new Consumer(queue);
		c.start();
	}
}

class Producer extends Thread{
	private BlockingQueue<String> queue;
	Producer(BlockingQueue<String> bq){
		queue = bq;
	}
	public void run(){
		for(int i=0; i<10; i++){
			try {
				int n = (int) (Math.random() * 10);
				Thread.sleep(n * 1000);
				String input = "Item" + i;
				queue.put(input);
				System.out.println("produce : " + input);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread{	
	private BlockingQueue<String> queue;
	Consumer(BlockingQueue<String> bq){
		queue = bq;
	}
	public void run(){
		for(int i=0; i<10; i++){
			try {
				int n = (int) (Math.random() * 10);
				Thread.sleep(n * 1000);
				String output = queue.take();
				System.out.println("comsume : " + output);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
