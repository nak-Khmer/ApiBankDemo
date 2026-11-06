# Stage 1: Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# ចម្លង pom.xml និងទាញយក dependencies ទុកក្នុង cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# ចម្លង Source Code និង Compile កូដចេញជា JAR file
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# បង្កើត Non-root user ដើម្បីសុវត្ថិភាព (Senior Best Practice)
RUN useradd -m -u 1001 appuser
USER appuser

# ចម្លង JAR file ចេញពី Stage 1
COPY --from=builder /app/target/*.jar app.jar

# បើក Port 8081
EXPOSE 8081

# Command សម្រាប់ដំណើរការ Spring Boot App
ENTRYPOINT ["java", "-jar", "app.jar"]