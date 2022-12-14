FROM openjdk:17-ea-jdk-oracle
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Profiler2022.jar
ENTRYPOINT ["java","-jar","/Profiler2022.jar"]