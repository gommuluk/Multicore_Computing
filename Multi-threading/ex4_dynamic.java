class TASKS{
	int taskNum = 0;
	
	public synchronized int getTaskNum() {
		return taskNum;
	}
	public synchronized int incTaskNum() {
		return taskNum++;
	}
}

class CountThread2 extends Thread {
	int lo, hi;
	int result = 0;
	long timeDiff = 0;
	TASKS tk;
	int task_unit, num_task;
	int gt = 0;
	long startTime,endTime;
	CountThread2(int i, int num_end, int num_task, TASKS tk){
		this.tk = tk;
		this.num_task = num_task;
		this.tk.taskNum++;
		task_unit = num_end/num_task;
		lo = task_unit*i;
		hi = task_unit*(i+1);
	}
	public void run(){
		
		startTime = System.currentTimeMillis();
		for(int i=lo; i<hi; i++){
			if(isPrime(i)) result++;
		}

		while(tk.getTaskNum() < num_task){
			gt = tk.incTaskNum();
			lo = task_unit*gt;
			hi = task_unit*(gt+1);
			for(int i=lo; i<hi; i++){
				if(isPrime(i)) result++;
			}
		}
		
		endTime = System.currentTimeMillis();
		timeDiff = endTime - startTime;
    	System.out.println(this.getName() + " execution time : " + timeDiff +"ms");		
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

class ex4_dynamic {
  private static final int NUM_END = 200000;
  private static final int NUM_THREAD = 16;
  private static final int NUM_TASK = 50;
  
  public static void main(String[] args) {
    int counter=0;
    int i;
    TASKS tk = new TASKS();
    long startTime = System.currentTimeMillis();
    CountThread2[] td = new CountThread2[NUM_THREAD];
    
    for (i=0;i<NUM_THREAD;i++) {
    	td[i] = new CountThread2(i,NUM_END,NUM_TASK,tk);
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
