# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-alpine

# JAR 파일을 이미지에 복사
ARG JAR_FILE=build/libs/quietseoul-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 포트 오픈
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
