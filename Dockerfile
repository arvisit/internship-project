FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/profiler/app.jar
ENTRYPOINT ["java","-jar","/opt/profiler/app.jar"]