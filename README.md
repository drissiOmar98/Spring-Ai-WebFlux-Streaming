# 🤖 Spring AI WebFlux Streaming

<div align="center">

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring_AI-latest-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-ai)
[![WebFlux](https://img.shields.io/badge/WebFlux-Reactive-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
[![Ollama](https://img.shields.io/badge/Ollama-LLaMA_3.2-black?style=for-the-badge&logo=ollama&logoColor=white)](https://ollama.ai/)
[![Vaadin](https://img.shields.io/badge/Vaadin-Flow-00B4F0?style=for-the-badge&logo=vaadin&logoColor=white)](https://vaadin.com/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

**A production-ready example demonstrating reliable LLM token streaming using Spring AI, WebFlux, and NDJSON — with backpressure handling, error recovery, and a Vaadin client.**

</div>

---

## 📖 Table of Contents

- [✨ Overview](#-overview)
- [🏗️ Architecture](#️-architecture)
- [🔑 Key Features](#-key-features)
- [📁 Project Structure](#-project-structure)
- [🛠️ Tech Stack](#️-tech-stack)
- [⚙️ Prerequisites](#️-prerequisites)
- [🚀 Getting Started](#-getting-started)
  - [Running with Docker Compose](#-running-with-docker-compose)
  - [Running Locally](#-running-locally)
- [🔌 Configuration](#-configuration)
- [💡 Why NDJSON over SSE?](#-why-ndjson-over-sse)


---

## ✨ Overview

This project demonstrates a **robust approach to streaming LLM responses** in a Java/Spring ecosystem. Instead of using Server-Sent Events (SSE), it leverages **NDJSON (Newline Delimited JSON)** over reactive WebFlux streams — providing better backpressure control, simpler error recovery, and greater client flexibility.

The stack integrates **Ollama** (local LLM runner), **Spring AI** (LLM abstraction), **Spring WebFlux** (reactive HTTP), and a **Vaadin Flow** frontend — all orchestrated via **Docker Compose** for a seamless developer experience.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Docker Network                        │
│                                                             │
│  ┌──────────────┐   NDJSON Stream   ┌──────────────────┐   │
│  │   Frontend   │ ◄──────────────── │     Backend       │   │
│  │ (Vaadin Flow)│                   │  (Spring WebFlux) │   │
│  │   :8081      │                   │      :8080        │   │
│  └──────────────┘                   └────────┬─────────┘   │
│                                              │              │
│                                   Spring AI  │              │
│                                              ▼              │
│                                    ┌─────────────────┐      │
│                                    │     Ollama       │      │
│                                    │ (llama3.2:1b/3b) │      │
│                                    │    :11434        │      │
│                                    └─────────────────┘      │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔑 Key Features

- ⚡ **Real-time LLM token streaming** — tokens are pushed to the client as they are generated, minimising perceived latency
- 📦 **NDJSON transport** — each token is a self-contained JSON object on its own line, making parsing trivial and robust
- 🔄 **Reactive backpressure** — Spring WebFlux honours consumer demand so a slow client never overwhelms memory
- 🛡️ **Error recovery** — stream errors are caught and surfaced gracefully without crashing the connection
- 🖥️ **Vaadin Flow client** — a modern Java-based UI consumes the NDJSON stream with live updates
- 🐳 **One-command deployment** — full stack (Ollama + backend + frontend) brought up with `docker-compose up -d`
- 🧩 **Multi-stage Dockerfiles** — lean production images for both the backend and frontend modules

---

## 📁 Project Structure

```
Spring-Ai-WebFlux-Streaming/
├── 📂 backend/                  # Spring Boot + Spring AI + WebFlux
│   ├── src/
│   │   └── main/java/           # Reactive controllers, AI service, NDJSON serialisation
│   ├── Dockerfile               # Multi-stage build for backend image
│   └── pom.xml
│
├── 📂 frontend/                 # Vaadin Flow application
│   ├── src/
│   │   └── main/java/           # Vaadin views, WebClient NDJSON consumer
│   ├── Dockerfile               # Multi-stage build for frontend image
│   └── pom.xml
│
├── 📂 ollama/
│   └── init/
│       └── run_ollama.sh        # Init script: pulls model & starts Ollama server
│
├── docker-compose.yml           # Wires up ollama, backend, and frontend services
├── pom.xml                      # Root Maven POM (multi-module)
└── README.md
```

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| 🧠 LLM Runtime | [Ollama](https://ollama.ai/) + LLaMA 3.2 | Local inference engine |
| 🤖 AI Abstraction | [Spring AI](https://spring.io/projects/spring-ai) | Chat client & streaming API |
| ⚙️ Backend | [Spring Boot](https://spring.io/projects/spring-boot) + [WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html) | Reactive HTTP server |
| 📡 Transport | NDJSON over HTTP | Streaming token delivery |
| 🖥️ Frontend | [Vaadin Flow](https://vaadin.com/) | Server-side Java UI |
| 🐳 Containerisation | [Docker](https://www.docker.com/) + [Docker Compose](https://docs.docker.com/compose/) | Local orchestration |
| ☕ Language | Java 25 | Primary language |
| 🔨 Build | Maven (multi-module) | Dependency & build management |

---

## ⚙️ Prerequisites

Make sure the following are installed on your machine before proceeding:

- ☕ **Java 21** or later — [Download here](https://openjdk.org/)
- 🐳 **Docker** & **Docker Compose** — [Download here](https://www.docker.com/products/docker-desktop)
- *(Optional for local run)* **Ollama CLI** — [Download here](https://ollama.ai/)

---

## 🚀 Getting Started

### 🐳 Running with Docker Compose

This is the recommended and easiest way to run the full stack.

**1. Clone the repository**

```bash
git clone https://github.com/drissiOmar98/Spring-Ai-WebFlux-Streaming.git
cd Spring-Ai-WebFlux-Streaming
```

**2. Start all services**

```bash
docker-compose up -d
```

This will spin up three containers:

| Container | Description | Port |
|---|---|---|
| `ollama-svq` | Ollama LLM server (auto-pulls `llama3.2:1b`) | `11434` |
| `backend` | Spring AI + WebFlux streaming API | `8080` |
| `frontend` | Vaadin Flow UI | `8081` |

**3. Open the application**

Navigate to [http://localhost:8081](http://localhost:8081) in your browser.

**4. View logs** *(optional)*

```bash
# All services
docker-compose logs -f

# A specific service
docker-compose logs -f backend
```

**5. Stop the stack**

```bash
docker-compose down
```

---

### 💻 Running Locally

Use this approach for active development with hot reload.

**1. Start Ollama**

```bash
# Option A — via Docker (recommended)
docker-compose up -d ollama

# Option B — via Ollama CLI
ollama pull llama3.2:3b
ollama serve
```

**2. Run the backend**

```bash
cd backend
./mvnw spring-boot:run
```

The backend API will be available at [http://localhost:8080](http://localhost:8080).

**3. Run the frontend** *(in a new terminal)*

```bash
cd frontend
./mvnw spring-boot:run
```

The Vaadin UI will be available at [http://localhost:8081](http://localhost:8081).

> 💡 **Tip:** On first run, Vaadin will download its frontend bundle which may take a minute. Subsequent starts are much faster.

---

## 🔌 Configuration

Key environment variables used by the services:

### Backend

| Variable | Default | Description |
|---|---|---|
| `SPRING_AI_OLLAMA_BASE_URL` | `http://ollama-svq:11434` | Ollama server URL |
| `SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL` | `llama3.2:1b` | LLM model to use |

### Frontend

| Variable | Default | Description |
|---|---|---|
| `BACKEND_URL` | `http://backend:8080` | Backend service URL |

### Ollama

| Variable | Value | Description |
|---|---|---|
| `OLLAMA_KEEP_ALIVE` | `-1` | Keep model loaded in memory indefinitely |
| `OLLAMA_DEBUG` | `2` | Log input prompts and responses |

> 💡 You can switch to a larger model (e.g., `llama3.2:3b`) by updating `SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL` in `docker-compose.yml`. Make sure your machine has enough RAM.

---

## 💡 Why NDJSON over SSE?

Server-Sent Events (SSE) are the most common way to stream data from server to client, but they come with limitations when streaming LLM tokens at scale.

| Concern | SSE | NDJSON over WebFlux |
|---|---|---|
| 🔄 Backpressure | ❌ No native support | ✅ Built-in via Project Reactor |
| 🛡️ Error recovery | ⚠️ Reconnect logic needed on client | ✅ Handled at stream level |
| 📦 Token format | Plain text or JSON string | Structured JSON objects per line |
| 🔗 Client compatibility | Browser-native, limited HTTP clients | Any HTTP client (curl, WebClient, fetch) |
| 🧹 Stream termination | Requires sentinel event | Natural EOF on stream completion |
| 🧪 Testability | Harder to unit-test | Easily testable with `StepVerifier` |

NDJSON gives you the simplicity of line-delimited text with the structure of JSON — and pairs perfectly with Spring WebFlux's `Flux<T>` model.




