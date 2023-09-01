FROM openjdk:17-jdk-slim
ARG JAR_FILE=app/api/build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} performance-service.jar
RUN chmod +x performance-service.jar
ENTRYPOINT [ "java", "-jar", "performance-service.jar" ]