# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# JAR 파일을 이미지에 복사
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} app.jar

# 포트 오픈
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
