FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
ARG JAR_FILE=target/spring-forex-api-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]