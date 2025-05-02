# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# JAR 파일을 이미지에 복사
# 첫 번째 브랜치에서 수정한 방식
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} app.jar

#COPY build/libs/app.jar app.jar

# 포트 오픈
EXPOSE 8080

# JVM 메모리 설정과 함께 JAR 실행
ENTRYPOINT ["java", "-Xmx384m", "-Xms256m", "-jar", "app.jar"]
