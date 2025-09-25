# Estágio de build usando a imagem do Maven a partir do repositório público da AWS
FROM public.ecr.aws/docker/library/maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Compila a aplicação e gera o arquivo .jar
RUN mvn clean package -DskipTests

# Estágio final usando uma imagem Java Runtime (JRE) a partir do repositório público da AWS
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o .jar gerado no estágio de build para a imagem final
COPY --from=build /app/target/liutis-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar a aplicação quando o container rodar
ENTRYPOINT ["java", "-jar", "app.jar"]