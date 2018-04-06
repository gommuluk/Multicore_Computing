import java.util.*;

class intPos {
	int x=0;
	int y=0;
	int k=0;
	intPos(int x, int y, int k){
		this.x = x;
		this.y = y;
		this.k = k;
	}
}

class Position {
	int x = 0, y = -1, K = 0;
	int MAX_X = 0, MAX_Y = 0;
	int ans[][];
	intPos ip = new intPos(0, 0, 0);
	
	Position(int mat_len_x, int mat_len_y, int mat_len_k){
		MAX_X = mat_len_x;
		MAX_Y = mat_len_y;
		K = mat_len_k;
		ans = new int[MAX_X][MAX_Y];
	}
	public synchronized intPos incPos(){
		if(y + 1 < MAX_Y) ++y;
		else {
			y = 0;
			x++;
		}
		if(x < MAX_X) return new intPos(x, y, K);
		else return null;
	}
}

class CalculusThread extends Thread {
	
	Position pos;
	int a[][], b[][];
	CalculusThread(Position pos, int a[][], int b[][]){
		this.pos = pos;
		this.a = a;
		this.b = b;
	}
	public void run(){
		
		long startTime = System.currentTimeMillis();
		while(true){
			
			intPos tmp = pos.incPos();
			if(tmp == null) break;
			
	        for(int kk = 0;kk < tmp.k;kk++){ 
	          pos.ans[tmp.x][tmp.y] += a[tmp.x][kk] * b[kk][tmp.y];
	        }
		}
		long endTime = System.currentTimeMillis();
        System.out.println(this.getName() + " finished : " + (endTime - startTime) + "ms");
	}

	    
}

class MatmultD
{
  private static Scanner sc = new Scanner(System.in);
  public static void main(String [] args)
  {
    int thread_no=0;

    if (args.length==1) thread_no = Integer.valueOf(args[0]);
    else thread_no = 1;
    
    int a[][]=readMatrix();
    int b[][]=readMatrix();
    Position pos = new Position(a.length, b[0].length, a[0].length);

    long startTime = System.currentTimeMillis();
    CalculusThread[] ct = new CalculusThread[thread_no];
    for(int i=0; i<thread_no; i++){
    	ct[i] = new CalculusThread(pos, a, b);
    	ct[i].start();
    }
  
    try{
	    for(int i=0; i<thread_no; i++){
	    	ct[i].join(); 
	    }
    } catch(InterruptedException e){}

    long endTime = System.currentTimeMillis();

    printMatrix(pos.ans);

    System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", thread_no, endTime-startTime);
  }

   public static int[][] readMatrix() {
       int rows = sc.nextInt();
       int cols = sc.nextInt();
       int[][] result = new int[rows][cols];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < cols; j++) {
              result[i][j] = sc.nextInt();
           }
       }
       return result;
   }

  public static void printMatrix(int[][] mat) {
  System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
    int rows = mat.length;
    int columns = mat[0].length;
    int sum = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
//        System.out.printf("%4d " , mat[i][j]);
        sum+=mat[i][j];
      }
//      System.out.println();
    }
    System.out.println();
    System.out.println("Matrix Sum = " + sum + "\n");
  }


}
