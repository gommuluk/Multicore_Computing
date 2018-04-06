#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#define NUM_END 200000

int isPrime(int x){
	int i;
	if (x <= 1) return 0;
	for (i = 2; i < x; i++) {
		if ((x%i == 0) && (i != x)) return 0;
	}
	return 1;
}
int main(int argc, char* argv[])
{
	int thread_type;
	int num_thread, i, sum = 0;
	double timeDiff = 0.0, start_time, end_time;

	if (argc==3) {
		thread_type=atoi(argv[1]);
		num_thread=atoi(argv[2]);
	} else {
		printf("wrong input! ex) a.out [thread number]\n");
		exit(0);
	}

	/* set thread number */
	omp_set_num_threads(num_thread); 
	start_time = omp_get_wtime(); 
	
	/* set schedule */
	if(thread_type==1) 
		omp_set_schedule(omp_sched_static, 4); 
    else if(thread_type==2) 
		omp_set_schedule(omp_sched_dynamic, 4);
    else if(thread_type==3) 
		omp_set_schedule(omp_sched_guided, 4);

	#pragma omp parallel for reduction(+:sum) schedule(runtime)
	for (i = 1; i <= NUM_END; i++){
		if (isPrime(i) == 1){
			sum++;
		}
	}

	end_time = omp_get_wtime();
	timeDiff = (end_time - start_time);
	printf("Execution Time : %lfs\n", timeDiff);
	printf("1...%d prime# counter=%d\n", NUM_END, sum);

	return 0;
}

