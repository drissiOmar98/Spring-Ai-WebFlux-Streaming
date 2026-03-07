package dev.omar.backendspring.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 🌟 Spring AI Configuration for the backend
 * <p>
 * This configuration class is responsible for creating and providing
 * a {@link ChatClient} bean to the Spring context.
 * <p>
 * 🛠 Features:
 * - Instantiates a {@link ChatClient} using the provided {@link ChatModel}
 * - Allows other beans or services to inject {@link ChatClient} and use it for LLM chat operations
 * - Fully managed by Spring's dependency injection
 */
@Configuration
public class AiConfig {

    /**
     * 🤖 Creates a ChatClient bean for interacting with LLM models.
     *
     * @param chatModel the chat model configuration to use (e.g., Ollama Llama3.2)
     * @return a fully initialized {@link ChatClient} ready to stream or send chat messages
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

}
