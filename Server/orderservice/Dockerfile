FROM maven:latest as build
WORKDIR /app
COPY ../. /app
WORKDIR /app/orderservice
RUN mvn dependency:resolve
RUN mvn clean
RUN mvn package -DskipTests -X

FROM openjdk:21
COPY --from=build /app/orderservice/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]