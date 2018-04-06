#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define NUM_CAR 40
#define NUM_SPACE 10

pthread_mutex_t mutex;
pthread_cond_t mut_cond;
int num_empty_space = NUM_SPACE;

void *get_into_garage(void *t);
void car_enter(long t);
void car_leave(long t);

int main(int argc, char *argv[])
{
	pthread_t car[NUM_CAR + 1];
	int rc, t;
	
	/* initialize mutex lock & conditional value  */
	pthread_mutex_init(&mutex, NULL);
	pthread_cond_init(&mut_cond, NULL);

	
	for (t=1; t<=NUM_CAR; t++) {
		rc = pthread_create(&car[t], NULL, get_into_garage, (void *)t);
		if (rc) {
			printf("ERROR; return code from pthread_create() is %d\n", rc);
			exit(-1);
		}
	}
	
	for (t=1; t<=NUM_CAR; t++) {
		pthread_join(car[t], NULL);
	}
	
	/* destroy mutex lock & conditional value  */
	pthread_mutex_destroy(&mutex);
	pthread_cond_destroy(&mut_cond);
	pthread_exit(NULL);
}

void *get_into_garage(void *t){
	int tid = (int)t;
	
	
	while (1){
		srand(tid);
		
		sleep(rand() % 10);
		car_enter(tid);
		
		sleep(rand() % 10);
		car_leave(tid);
	}

	pthread_exit((void*)t);
}

void car_enter(long t){
	pthread_mutex_lock(&mutex);
	while (num_empty_space == 0){
		pthread_cond_wait(&mut_cond, &mutex);
	}
	num_empty_space--;
	printf("Car %d : entered \n", t);
	pthread_mutex_unlock(&mutex);
}

void car_leave(long t)
{
	pthread_mutex_lock(&mutex);
	num_empty_space++;
	pthread_cond_signal(&mut_cond);
	printf("Car %d : leaved \n", t);
	pthread_mutex_unlock(&mutex);
}
