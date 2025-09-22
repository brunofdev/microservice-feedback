# Usa uma imagem base com Java 21 do Eclipse Temurin.
FROM eclipse-temurin:21-jdk

# Define o diretorio de trabalho no container
WORKDIR /feedbackservice

# Copia o arquivo JAR do seu projeto para o container
COPY target/feedbackservice-0.0.1-SNAPSHOT.jar app.jar

# Configura as variaveis de ambiente para o banco de dados
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/feedbackdb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=S4i4D4M4tr1X

# Expoe a porta que o Spring Boot usa (padrao 8080)
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]