class CountThread1 extends Thread {
	int lo, hi;
	int result = 0;
	long timeDiff = 0;
	CountThread1(int l, int h){
		lo = l; hi = h;
	}
	public void run(){
		long startTime = System.currentTimeMillis();
		for(int i=lo; i<hi; i++){
			if(isPrime(i)) result++;
		}
		long endTime = System.currentTimeMillis();
		timeDiff = endTime - startTime;
		System.out.println(this.getName() + " execution time : " + timeDiff+"ms");
	}
	private static boolean isPrime(int x) {
	    int i;
	    if (x<=1) return false;
	    for (i=2;i<x;i++) {
	      if ((x%i == 0) && (i!=x)) return false;
	    }
	    return true;			
	 }
}

class ex4_static {
  private static final int NUM_END = 200000;
  private static final int NUM_THREAD = 16;
  
  public static void main(String[] args) {
    int counter=0;
    int i;

    long startTime = System.currentTimeMillis();
    CountThread1[] td = new CountThread1[NUM_THREAD];
    
    for (i=0;i<NUM_THREAD;i++) {
    	td[i] = new CountThread1(NUM_END*i/NUM_THREAD, NUM_END*(i+1)/NUM_THREAD);
    	td[i].start();
    }
    try{
        for (i=0;i<NUM_THREAD;i++) {
        	td[i].join();
        	counter += td[i].result;
        }
    } catch(InterruptedException e){}
    
    long endTime = System.currentTimeMillis();
    long timeDiff = endTime - startTime;
    System.out.println("\nMain thread execution time : "+timeDiff+"ms");
    System.out.println("1..."+(NUM_END-1)+" prime# counter=" + counter +"\n");
  }

  
}
