# 베이스 이미지 지정: Java 17이 설치된 경량 리눅스 이미지를 사용
FROM openjdk:17-jdk-slim

# 빌드된 JAR 파일을 가져올 경로를 지정 (Gradle 빌드시 생성되는 경로)
ARG JAR_FILE=build/libs/js-be.jar

# JAR 파일을 컨테이너 내부의 /app.jar로 복사
COPY ${JAR_FILE} app.jar

# 컨테이너가 시작될 때 실행할 명령어 지정 (JAR 파일 실행)
ENTRYPOINT ["java", "-jar", "/app.jar"]