# JAR 빌드용 Gradle 이미지
FROM gradle:8.1.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# 실행용 경량 이미지
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
