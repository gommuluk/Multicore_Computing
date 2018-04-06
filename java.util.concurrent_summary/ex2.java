import java.util.concurrent.Semaphore;

public class ex2 {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(2);
		
		Thread[] th = new TestSemaphore[10];
		for(int i=0;i<10;i++){
			th[i] = new TestSemaphore(semaphore, i);
			th[i].start();
		}	
	}
}

class TestSemaphore extends Thread{
	private int tid;
	private Semaphore semaphore;
	TestSemaphore(Semaphore sp, int i){
		semaphore = sp;
		tid = i;
	}
	public void run(){
		try {
			semaphore.acquire();
			System.out.println("Thread " + tid + " : acquire");
			Thread.sleep(1000);
			System.out.println("Thread " + tid + " : release");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}