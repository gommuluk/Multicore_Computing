Linux �Ǵ� Cygwin���� ������ �� �ֽ��ϴ�.

1. openmp_ray.exe �����ϱ�
>> ./openmp_ray.exe [thread_num] [result_file_name.ppm]
Ex) ./openmp_ray.exe 8 result8.ppm

2. cuda_ray.exe �����ϱ�
CUDA ������ ���� ���� ���� OSȯ�濡 �ش��ϴ� CUDA�� �ٿ�޾ƾ� �Ѵ�. ��ġ ������ Visual Studio 2015 ���� ������ ��ġ�Ǿ� �־�� �Ѵ�.  
�� https://developer.nvidia.com/cuda-downloads
��C:\ProgramData\NVIDIA Corporation\CUDA Samples\v.8.0.6\0_Simple\matrixMul������ ����ϴ� Visual Studio ������ �´� Solution������ �����Ѵ�. 
������Ʈ�� Build�Ͽ� Setting�� �Ϸ��Ѵ�.  

>> ./cuda_ray.exe [result_file_name.ppm]
Ex) ./cuda_ray.exe result.ppm
