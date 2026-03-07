# 🏗 Stage 1: Build the Spring Boot backend
FROM bellsoft/liberica-runtime-container:jre-25-musl as builder

# Set working directory inside the container
WORKDIR /app

# Copy the backend source code into the container
COPY . /app/backend

# Build the backend using Maven Wrapper
RUN cd backend && ./mvnw package

# ⚡ Stage 2: Optimize the JAR for layered image
FROM bellsoft/liberica-runtime-container:jre-25-musl as optimizer
WORKDIR /app

# Copy the packaged backend JAR from the builder stage
COPY --from=builder /app/backend/target/backend-*.jar app.jar

# Extract the Spring Boot layered JAR for efficient Docker caching
RUN java -Djarmode=tools -jar /app/app.jar extract --layers --destination extracted


# 🏃 Stage 3: Final lightweight runtime image
FROM bellsoft/liberica-runtime-container:jre-25-slim-musl

WORKDIR /app

# Copy optimized layers into the final image
COPY --from=optimizer /app/extracted/dependencies/ ./
COPY --from=optimizer /app/extracted/spring-boot-loader/ ./
COPY --from=optimizer /app/extracted/snapshot-dependencies/ ./
COPY --from=optimizer /app/extracted/application/ ./

# ⚡ Set the entrypoint to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]