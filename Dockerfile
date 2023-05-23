FROM maven:3.8.6-amazoncorretto-17 AS build
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn package -DskipTests

# Run stage
FROM openjdk:17-alpine
ARG JAR_FILE=/build/target/*.jar
RUN mkdir -p /opt/profiler/photo
COPY --from=build $JAR_FILE /opt/profiler/app.jar
ENTRYPOINT ["java", "-jar", "/opt/profiler/app.jar"]
