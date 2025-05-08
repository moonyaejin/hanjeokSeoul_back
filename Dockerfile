FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app

COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle

RUN ./gradlew dependencies --no-daemon || true

COPY . .

# JAR 파일 빌드
RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx384m", "-Xms256m", "-jar", "app.jar"]
