# 1. Usa una imagen base de Maven para la construcción
FROM maven:3.8.5-openjdk-17 AS build

# 2. Configura el directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copia los archivos de Maven (pom.xml y código fuente)
COPY pom.xml .
COPY src ./src

# 4. Construye la aplicación y crea el archivo .jar
RUN mvn clean package -DskipTests

# 5. Usa una imagen base de JDK/JRE para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# 6. Copia el archivo .jar creado en la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# 7. Expone el puerto (ajústalo si tu aplicación usa un puerto específico)
EXPOSE 8080

# 8. Define el comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
