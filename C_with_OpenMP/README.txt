==READ_ME==

1. 동작 환경 
	이 코드는 Unix/Linux 환경에서 동작하는 코드입니다. 
	Windows에서는 cygwin을 통해 실행할 수 있습니다. 

2. 컴파일 
	gcc와 -fopenmp 플래그를 사용합니다. 
	ex) gcc prob1.c -fopenmp
	prob1.c 파일과 같은 디렉토리에서 a.exe 파일을 확인할 수 있습니다. 
	
3. 실행
	생성된 파일 이름과 스케줄 타입(1 = static, 2 = dynamic, 3 = guided), 사용할 스레드 개수를 입력합니다. 
	ex) ./a.exe 3 4
		(3번 스케줄 타입인 guided에서 스레드 4개를 사용하여 실행하게 됩니다. )
		
4. 결과 확인
	화면에서 실행 시간과 소수 개수를 확인할 수 있습니다. 

