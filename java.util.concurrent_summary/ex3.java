import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ex3 {

	public static void main(String[] args) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		Thread[] thR = new Reader[5];
		Thread[] thW = new Writer[5];
		for(int i=0;i<5;i++){
			thR[i] = new Reader(readWriteLock, i);
			thW[i] = new Writer(readWriteLock, i);
			thR[i].start();
			thW[i].start();
		}
	}
}

class Reader extends Thread{
	private int tid;
	private ReadWriteLock lock;
	private int rd;
	
	Reader(ReadWriteLock lock, int i){
		this.lock = lock;
		tid = i;
	}
	public void run(){
		lock.readLock().lock();
		
		rd = (int) Math.random() * 10;
		try {
			System.out.println("Thread " + tid + " is reading");
			Thread.sleep(rd * 1000);
			System.out.println("Thread " + tid + " done reading");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		lock.readLock().unlock();
	}
}


class Writer extends Thread{
	private int tid;
	private ReadWriteLock lock;
	private int rd;
	
	Writer(ReadWriteLock lock, int i){
		this.lock = lock;
		tid = i;
	}
	public void run(){
		lock.writeLock().lock();
		rd = (int) Math.random() * 10;
		
		try {
			System.out.println("Thread " + tid + " is writing alone");
			Thread.sleep(rd * 1000);
			System.out.println("Thread " + tid + " done writing");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		lock.writeLock().unlock();
	}
}