FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} discovery-server.jar
EXPOSE 8761/tcp
ENTRYPOINT [ "java", "-jar", "/discovery-server.jar" ]