FROM openjdk:11-jre-slim
LABEL maintainer="victor kiambi"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]