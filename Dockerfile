# Estagio 1: Compilacao
FROM maven:3-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Estagio 2: Runtime da aplicacao
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/feedbackservice-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]