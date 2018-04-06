Linux 또는 Cygwin에서 실행할 수 있습니다.

1. openmp_ray.exe 실행하기
>> ./openmp_ray.exe [thread_num] [result_file_name.ppm]
Ex) ./openmp_ray.exe 8 result8.ppm

2. cuda_ray.exe 실행하기
CUDA 실행을 위해 먼저 실행 OS환경에 해당하는 CUDA를 다운받아야 한다. 설치 전에는 Visual Studio 2015 이하 버전이 설치되어 있어야 한다.  
→ https://developer.nvidia.com/cuda-downloads
“C:\ProgramData\NVIDIA Corporation\CUDA Samples\v.8.0.6\0_Simple\matrixMul”에서 사용하는 Visual Studio 버전에 맞는 Solution파일을 실행한다. 
프로젝트를 Build하여 Setting을 완료한다.  

>> ./cuda_ray.exe [result_file_name.ppm]
Ex) ./cuda_ray.exe result.ppm
