
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define SPHERES 20

#define rnd( x ) (x * rand() / RAND_MAX)
#define INF 2e10f
#define DIM 2048

struct Sphere {
	float   r, b, g;
	float   radius;
	float   x, y, z;
};

/* Added __global__ variable to run kernel function in GPU */
__global__ void kernel(const Sphere* s, unsigned char* ptr)
{
	/* Each kernel function uses thread/block index and block dimension to determine a unique number to process a particular pixel (x, y) */
	int x = threadIdx.x + blockIdx.x * blockDim.x;
	int y = threadIdx.y + blockIdx.y * blockDim.y;
	if (x >= DIM || y >= DIM) return;

	int offset = x + y * DIM;
	float ox = (x - DIM / 2);
	float oy = (y - DIM / 2);

	float r = 0, g = 0, b = 0;
	float   maxz = -INF;

	for (int i = 0; i<SPHERES; i++) {
		float   n;
		float   t;

		/* Moved the 'hit' function from the 'Sphere' structure of the existing code */
		float dx = ox - s[i].x;
		float dy = oy - s[i].y;

		if (dx*dx + dy*dy < s[i].radius*s[i].radius) {
			float dz = sqrtf(s[i].radius*s[i].radius - dx*dx - dy*dy);
			n = dz / sqrtf(s[i].radius * s[i].radius);
			t = dz + s[i].z;
		}
		else t = -INF;

		if (t > maxz) {
			float fscale = n;
			r = s[i].r * fscale;
			g = s[i].g * fscale;
			b = s[i].b * fscale;
			maxz = t;
		}
	}

	ptr[offset * 4 + 0] = (int)(r * 255);
	ptr[offset * 4 + 1] = (int)(g * 255);
	ptr[offset * 4 + 2] = (int)(b * 255);
	ptr[offset * 4 + 3] = 255;
}

void ppm_write(unsigned char* bitmap, int xdim, int ydim, FILE* fp)
{
	int i, x, y;
	fprintf(fp, "P3\n");
	fprintf(fp, "%d %d\n", xdim, ydim);
	fprintf(fp, "255\n");
	for (y = 0; y<ydim; y++) {
		for (x = 0; x<xdim; x++) {
			i = x + y*xdim;
			fprintf(fp, "%d %d %d ", bitmap[4 * i], bitmap[4 * i + 1], bitmap[4 * i + 2]);
		}
		fprintf(fp, "\n");
	}
}

int main(int argc, char* argv[])
{
	int x, y;
	unsigned char* bitmap;
	cudaEvent_t start, stop;  // for time measurement
	float timeDiff;

	/* time variables event create */
	cudaEventCreate(&start);
	cudaEventCreate(&stop);
	srand(time(NULL));

	if (argc != 2) {
		printf("> a.out [filename.ppm]\n");
		exit(0);
	}
	FILE* fp = fopen(argv[1], "w");

	Sphere *temp_s = (Sphere*)malloc(sizeof(Sphere) * SPHERES);
	for (int i = 0; i<SPHERES; i++) {
		temp_s[i].r = rnd(1.0f);
		temp_s[i].g = rnd(1.0f);
		temp_s[i].b = rnd(1.0f);
		temp_s[i].x = rnd(2000.0f) - 1000;
		temp_s[i].y = rnd(2000.0f) - 1000;
		temp_s[i].z = rnd(2000.0f) - 1000;
		temp_s[i].radius = rnd(200.0f) + 40;
	}

	bitmap = (unsigned char*)malloc(sizeof(unsigned char)*DIM*DIM * 4);

	/* device_s and device_bitmap is to be assigned to device */
	Sphere *device_s;
	unsigned char* device_bitmap;

	/* Allocate space on GPU to copy the temp_s */
	cudaMalloc((void**)&device_s, sizeof(Sphere)*SPHERES);
	cudaMalloc((void**)&device_bitmap, sizeof(unsigned char)*DIM*DIM * 4);

	/* Copy temp_s to device_s to run the function in GPU */
	cudaMemcpy(device_s, temp_s, sizeof(Sphere)*SPHERES, cudaMemcpyHostToDevice);

	/* Start the recoding */
	cudaEventRecord(start, 0);

	/* 768 thread per block */
	dim3 dimBlock(32, 24);
	/* (Dimension/blockDimension) block per grid */
	dim3 dimGrid(DIM / dimBlock.x, DIM / dimBlock.y);

	kernel << <dimGrid, dimBlock >> > (device_s, device_bitmap);
	cudaDeviceSynchronize();

	/* End the recording */
	cudaEventRecord(stop, 0);
	cudaEventSynchronize(stop);

	/* Copy the result stored in the device back to the host */
	cudaMemcpy(bitmap, device_bitmap, sizeof(unsigned char)*DIM*DIM * 4, cudaMemcpyDeviceToHost);

	/* Execution time checking */
	cudaEventElapsedTime(&timeDiff, start, stop);
	printf("CUDA ray tracing: %f sec \n", timeDiff / CLOCKS_PER_SEC);

	ppm_write(bitmap, DIM, DIM, fp);
	printf("[%s] was generated. \n", argv[1]);

	fclose(fp);
	free(bitmap);
	free(temp_s);

	cudaFree(device_s);
	cudaFree(device_bitmap);

	return 0;
}