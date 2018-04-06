import java.util.concurrent.atomic.AtomicInteger;

public class ex4 {

	public static void main(String[] args) {
		
		AtomicInteger atomicInteger = new AtomicInteger();

		Thread[] th = new TestAtomicInteger[5];
		for(int i=0;i<5;i++){
			th[i] = new TestAtomicInteger(atomicInteger, i);
			th[i].start();
		}
	}
}

class TestAtomicInteger extends Thread{
	private int tid;
	private AtomicInteger atomicInteger;
	TestAtomicInteger(AtomicInteger ai, int i){
		atomicInteger = ai;
		tid = i + 1;
	}
	public void run(){
		try {
			Thread.sleep(tid * 500);
			int get = atomicInteger.get();
			System.out.println("Thread " + tid + " gets " + get);
			Thread.sleep(tid * 500);
			int getNadd = atomicInteger.getAndAdd(tid);
			System.out.println("Thread " + tid + " gets and add " + getNadd);
			Thread.sleep(tid * 500);
			int addNget = atomicInteger.addAndGet(tid);
			System.out.println("Thread " + tid + " add and gets " + addNget);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

