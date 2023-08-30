FROM openjdk:17-jdk-slim
ARG JAR_FILE=app/api/build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} member-service.jar
RUN chmod +x member-service.jar
ENTRYPOINT [ "java", "-jar", "member-service.jar" ]