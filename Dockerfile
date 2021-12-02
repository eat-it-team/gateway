FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . .
RUN mvn package

FROM openjdk:8-jdk-alpine

ARG JAR_FILE=gateway-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","gateway-0.0.1-SNAPSHOT.jar"]