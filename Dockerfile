# MUDANÇA AQUI: Trocamos a fonte da imagem para o repositório da AWS
FROM public.ecr.aws/docker/library/maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# MUDANÇA AQUI: Trocamos a fonte da imagem para o repositório da AWS
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/target/liutis-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]