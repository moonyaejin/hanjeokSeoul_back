# 1단계: Gradle 빌드 스테이지
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app

# 캐시 최적화용: 기본 빌드 설정 먼저 복사
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle

# 의존성만 미리 받아두기 (캐시 활용)
RUN ./gradlew dependencies --no-daemon || true

# 나머지 전체 소스 복사
COPY . .

# ✅ 실행 가능한 Spring Boot JAR만 생성
RUN ./gradlew clean bootJar --no-daemon

# 2단계: 실제 실행 이미지
FROM openjdk:17-jdk-slim
WORKDIR /app

# ✅ 실행 가능한 JAR만 복사
COPY --from=build /app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx384m", "-Xms256m", "-jar", "app.jar"]
