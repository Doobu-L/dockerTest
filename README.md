# dockerTest

### 도커란?
애플리케이션 구동에 필요한 모든 것을 패키징하는 오픈소스 소프트웨어로 이해하였다.
한번 구축한 프로젝트를 어디에서든 구동이 가능하다. Docker Engine 위에서라면!
마치 JAVA코드가 JVM만있으면 어디서든 구동되는 방식의 확장판처럼.
VM은 하드웨어를 가상화하고 가상의 OS를 별도로 구축하고 구동하는데 많은 시간이 걸리고 CPU의 리소스도 많이 차지한다.

도커는 Host의 OS를 공유하기 때문에 VM과 OS를 가상화하는 부분에서 가장 큰 차이점이 있고,  리눅스의 커널을 공유하지만 
격리된 프로세스의 개념인 컨테이너에서 왔기 때문에 가볍고 빠르다.

github에서 우리가 코드의 형상관리를 유용하게 해낼 수 있다면
dockerhub에서는 서비스의 거시적인 관점에서의 관리를 유용하게 할 수 있어 보였다.
물론 git에도 package라는 서비스를 제공한다고 하지만 아직은 도커의 위상이 높다.

### 여기에서는
SpringBoot + MariaDB 를 docker-compose로  docker에 구동시켰다.

## 우선 Dockerfile을 통해 이미지 생성과 도커에 이미지를 구동

1. 기본적인 요청(GET hello)만 있는 서비스를 만들었다.
2. docker for window 인 docker desktop을 설치했다.
   작업은 terminel에서 cli로 했다.
3. Dockerfile 을 root path 에 생성하고 패키징을 위한 명세를 했다.
   
      FROM adoptopenjdk/openjdk8
      CMD ["./mvnw","clean","package"]
      ARG JAR_FILE_PATH=target/*.jar
      COPY ${JAR_FILE_PATH} app.jar
      ENTRYPOINT ["java","-jar","app.jar"]
      
4. docker build -t dockertest:0.5 . 
   root path 에서 명렁어로 빌드를 한다.  docker가 Dockerfile을 찾아서 -t 로 명명한 dockertest  0.5 version  의 이미지를 생성한다.
   
   ![image](https://user-images.githubusercontent.com/60733417/177296832-9ab83748-f970-4bec-b2c4-10db13f17e9e.png)
5. docker images 로 생성된 이미지 확인

6. docker run -p 7777:7777 dockertest:0.5 
   -p 7777:7777  는 도커가 실행되는 tcp layer에 7777포트를 로컬 7777포트에 포워딩 시키는 것. 앞에가 로컬.
![image](https://user-images.githubusercontent.com/60733417/177297770-faedd51a-f5ad-4dd3-9c4f-c760225c636a.png)

7. docker ps
   도커프로세서 확인.

## docker-compose

docker pull mariadb
docker container run -d -p 3306:3306 -e MARIADB_ROOT_PASSWORD=<Password> --name <Image이름> mariadb

1. +++DB 연동하고 Hibernate DDL - create (테스트를 위해 아주 꺠끗한 상태에서 테이블생성까지 가기위해)
   기본 insert / select 처리할 수 있는 요청 만듬.
2. root path에 docker-compose.yml (경로는 상관없고 테스트를위해 빠르게 생성함)
![image](https://user-images.githubusercontent.com/60733417/177300699-e3baffef-8554-4e8c-acbb-e10379346737.png)

- version : {docker-compose의 버전}      - docker-compose version 으로 확인
- service : 
   database : 
     container_name: {구동 될 컨테이너명}
     image: {구동 할 이미지파일명}
     
   application : 
      ebvurinebt:
         SPRING_DATASOURCE_URL: jdbc:mariadb://{container_name}:3306/{database_name}
         ..
         ..
       depends_on:
         -mariadb    <- mariadb 의존이 필요하다는 뜻. mariadb 후에 구동된다.

3. docker-compose up 으로 구동 (docker-compose.yml 위치에서 cli)

![image](https://user-images.githubusercontent.com/60733417/177303373-0c3bcfdb-0bfc-4df9-bf9c-d33badb7c7af.png)


### Docker Desktop 
![image](https://user-images.githubusercontent.com/60733417/177303654-c519a323-5437-4cc1-903f-ecfbbd8190e4.png)

### docker-compose up -d    백그라운드 구동  , docker-compose down  서비스 종료
![image](https://user-images.githubusercontent.com/60733417/177303993-eba45a97-49d5-404f-8d5a-d881590cad06.png)

