FROM openjdk:17-jdk-slim
COPY build/libs/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]
