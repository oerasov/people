FROM openjdk:8-jdk-alpine as build
WORKDIR workspace

COPY / ./

RUN ./gradlew build

FROM openjdk:8-jdk-alpine

COPY --from=build /workspace/build/libs/*.jar mainApp.jar

ENTRYPOINT ["java","-jar","mainApp.jar"]