FROM openjdk:8-jdk-alpine as build
WORKDIR workspace

COPY / ./

RUN ./gradlew build

FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

COPY --from=build /workspace/build/libs/people-0.0.1-SNAPSHOT.jar mainApp.jar

ENTRYPOINT ["java","-jar","mainApp.jar"]
