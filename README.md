# 🤖 Spring AI Showcase: Advanced LLM Integrations

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0+-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-1.1.2-blue.svg)](https://spring.io/projects/spring-ai)
[![OpenAI](https://img.shields.io/badge/OpenAI-GPT--4o-purple.svg)](https://openai.com/)
[![Google Gemini](https://img.shields.io/badge/Google-Gemini-blue.svg)](https://ai.google/)
A comprehensive demonstration project showcasing the power of **Spring AI** to build intelligent, AI-native applications. This repository integrates industry-leading LLMs like **OpenAI (GPT-4o)** and **Google Gemini** to solve real-world problems using modern AI patterns.

---

## 🌟 Key Highlights

This project isn't just a "Hello World" for AI. It implements advanced patterns that showcase the use cases of Spring AI in modern AI enabled systems:

-   **Multi-Model Orchestration**: Seamlessly switching between OpenAI and Google Gemini using Spring AI's abstraction layer.
-   **RAG (Retrieval-Augmented Generation)**: Implementation of the "Stuff the Prompt" pattern and Vector Store integration for domain-specific knowledge.
-   **Multimodal Capabilities**: Handling text, image generation (DALL-E 3), image analysis, and audio processing.
-   **Structured Output**: Converting unstructured LLM responses into strongly-typed Java POJOs for reliable API contracts.
-   **Function Calling & Tools**: Enabling LLMs to execute local Java methods (e.g., dynamic date/time retrieval) to bridge the gap between static training data and real-time information.
-   **Conversational Memory**: Using `ChatMemory` advisors to maintain context across stateless HTTP requests.

---

## 🚀 Features

### 💬 Chat & Interaction
-   **Standard Chat**: Basic request-response interactions.
-   **Streaming API**: Real-time token streaming for responsive UIs (using Project Reactor's `Flux`).
-   **System Prompts**: Steering AI behavior with complex personas and guidelines (e.g., Blog Post Generator).

### 🖼️ Multimodal AI
-   **Image Generation**: DALL-E 3 integration for high-quality image creation.
-   **Image Recognition**: Analyzing visual content to extract descriptions or data.
-   **Audio Processing**: Integration with speech-to-text or text-to-speech workflows.

### 📚 Knowledge & Data
-   **RAG Implementation**: Enhancing LLM accuracy by providing relevant context from JSON data sources and Vector Stores.
-   **Structured JSON Mapping**: Automatic deserialization of AI responses into `Itinerary`, `Activity`, and `Model` classes.

### 🛠️ Extensibility
-   **Custom Tools**: Registered Java functions that the AI can "call" to perform specific tasks.
-   **Chat Advisors**: Modular logic for logging, memory, and prompt augmentation.

---

## 🛠️ Tech Stack

-   **Backend**: Java 17, Spring Boot 3.5.x
-   **AI Framework**: Spring AI (OpenAI & Google GenAI Starters)
-   **Data Handling**: Jackson (JSON), Spring AI Vector Stores
-   **Build Tool**: Maven

---

## 🚦 Getting Started

### Prerequisites
-   Java 17 or higher
-   An OpenAI API Key
-   A Google Gemini API Key

### Configuration
Update your `src/main/resources/application.properties`:

```properties
# OpenAI Configuration
spring.ai.openai.api-key=${OPENAI_API_KEY}

# Google Gemini Configuration
spring.ai.google.genai.api-key=${GEMINI_API_KEY}
```

### Running the Project
```bash
./mvnw spring-boot:run
```

---

## 🔗 API Endpoints (Samples)

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/chat` | `GET` | Basic AI chat interaction |
| `/stream` | `GET` | Streaming chat response for Himachal trip |
| `/images/generate` | `GET` | Generate images via DALL-E 3 |
| `/rag/models` | `GET` | RAG-enhanced model information |
| `/vacation/structured`| `GET` | Get a structured JSON itinerary |
| `/tools` | `GET` | Demo of AI using local Java tools |

---

## 👨‍💻 About the Author

I am a Backend Developer passionate about the intersection of Enterprise Java and Artificial Intelligence. This project serves as a portfolio of my ability to integrate complex AI workflows into robust Spring Boot environments.

**Connect with me:**
- [LinkedIn](https://www.linkedin.com/in/vickyajmera120/)
- [GitHub](https://github.com/vickyajmera120)

---
*Built with ❤️ using Spring AI.*
