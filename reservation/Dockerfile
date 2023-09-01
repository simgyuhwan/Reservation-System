FROM openjdk:17-jdk-slim
ARG JAR_FILE=boot/boot-api/build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} reservation-service.jar
RUN chmod +x reservation-service.jar
ENTRYPOINT [ "java", "-jar", "reservation-service.jar" ]