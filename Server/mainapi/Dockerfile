FROM maven:latest as build
WORKDIR /app
COPY ../. /app
WORKDIR /app/mainapi
RUN mvn dependency:resolve
RUN mvn clean
RUN mvn package -DskipTests -X

FROM openjdk:21
COPY --from=build /app/mainapi/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]