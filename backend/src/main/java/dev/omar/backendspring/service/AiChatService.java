package dev.omar.backendspring.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * 🤖 AiChatService
 *
 * This service handles interaction with the LLM (Large Language Model)
 * via the Spring AI {@link ChatClient}.
 *
 * 🛠 Features:
 * - Sends user messages to the LLM
 * - Applies a default system prompt for concise responses
 * - Streams the response content reactively using {@link Flux}
 */
@Service
public class AiChatService {

    private final ChatClient chatClient;

    /** 📜 Default system prompt to guide LLM responses */
    private final String CHAT_PROMPT = """
                        Be concise. Answer in <=3 sentences unless asked otherwise.
            """;

    public AiChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 🔄 Stream LLM replies for a given user message
     *
     * @param userMessage the message from the user
     * @return a Flux stream of strings containing the LLM's reply
     */
    public Flux<String> reply(String userMessage) {
        return chatClient
                .prompt()
                .system(CHAT_PROMPT)
                .user(userMessage)
                .stream()
                .content();
    }

}