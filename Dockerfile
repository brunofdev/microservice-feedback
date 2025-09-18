# -- ESTÁGIO 1: CONSTRUÇÃO DA APLICAÇÃO --
# Usa uma imagem com Maven e um JDK 21 que existe
FROM maven:3.9.6-sapmachine-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# -- ESTÁGIO 2: CRIAÇÃO DA IMAGEM FINAL --
# Usa uma imagem base leve com o mesmo JDK
FROM openjdk:21-jdk-slim
WORKDIR /app
# Copia o arquivo JAR do estágio de construção
COPY --from=build /app/target/*.jar app.jar
# Define o ponto de entrada para a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]