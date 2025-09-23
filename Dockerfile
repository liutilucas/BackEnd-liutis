# Usar uma imagem base oficial do Java 17
FROM eclipse-temurin:17-jdk-jammy

# Definir o diretório de trabalho dentro da "caixa"
WORKDIR /app

# Copiar o nosso ficheiro .jar compilado para dentro da "caixa"
COPY target/liutis-0.0.1-SNAPSHOT.jar app.jar

# Comando para executar a aplicação quando a "caixa" ligar
ENTRYPOINT ["java", "-jar", "app.jar"]