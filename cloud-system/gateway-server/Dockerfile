FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} gateway-server.jar
EXPOSE 8000/tcp
ENTRYPOINT [ "java", "-jar", "/gateway-server.jar" ]