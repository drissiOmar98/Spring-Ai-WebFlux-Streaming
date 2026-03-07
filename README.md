# Spring AI Streaming Demo

Project for demonstrating the approach to streaming AI responses with NDJSON.

## Project Structure

The project consists of two main modules:

1. **frontend**: provides the Vaadin-based UI
2. **backend**: provides AI-powered responses using Ollama

## Prerequisites

- Java 25
- Docker and Docker Compose

## Running the Application

### Using Docker Compose

1. Build and start the containers:
   ```bash
   docker-compose up -d
   ```

2. Access the frontend at http://localhost:8081

### Running Locally

1. Start Ollama:
   ```bash
   docker-compose up -d ollama
   ```
   
   Alternatively, you can run Ollama locally:
   ```bash
   ollama pull llama3.2:3b
   ollama serve
   ```

2. Build and run the frontend module:
   ```bash
   cd frontend
   ./mvnw spring-boot:run
   ```

3. Build and run the backend module:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
