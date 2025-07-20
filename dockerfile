# Stage 1: Build
FROM maven:3.8.6-openjdk-17-slim AS build

# Define diretório de trabalho  
WORKDIR /app

# Copia pom.xml e o código-fonte  
COPY pom.xml .
COPY src ./src

# Compila e empacota a aplicação (skip tests para acelerar)  
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-jammy

# Diretório de execução  
WORKDIR /app

# Copia o jar gerado no estágio de build  
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que sua aplicação usa (por padrão é 8080)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
