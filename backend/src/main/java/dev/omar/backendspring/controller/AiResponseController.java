package dev.omar.backendspring.controller;

import dev.omar.backendspring.service.AiChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * 🌐 AiResponseController
 *
 * This REST controller exposes an endpoint for streaming AI responses.
 * It connects the {@link AiChatService} with the client via NDJSON streaming.
 *
 * 🛠 Features:
 * - POST /chat endpoint that consumes plain text requests
 * - Streams AI responses token by token using NDJSON (Server-Sent Events alternative)
 * - Handles errors gracefully, streaming them as part of the response
 * - Applies backpressure with limitRate to prevent overwhelming the client
 * - Logs stream start and end events for debugging
 *
 * 🔑 Usage:
 * Send a POST request with a plain text body to `/chat` and consume the response as NDJSON.
 * Each object in the stream will have:
 * <pre>
 * {
 *     "type": "token" | "error" | "done",
 *     "content": "..."
 * }
 * </pre>
 */
@RestController
public class AiResponseController {

    private final AiChatService aiChatService;
    private static final Logger log = LoggerFactory.getLogger(AiResponseController.class);


    public AiResponseController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    /**
     * 🔄 Stream AI chat responses reactively
     *
     * @param request the user's input message
     * @return a Flux of token maps in NDJSON format
     */
    @PostMapping(
            value = "/chat",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    public Flux<Map<String, String>> chat(@RequestBody String request) {
        return aiChatService.reply(request)
                // Map each token to a standard structure
                .map(text -> Map.of("type", "token", "content", text))
                // Handle errors as part of the stream
                .onErrorResume(ex -> Flux.just(Map.of(
                        "type", "error",
                        "content", ex.getMessage()
                )))
                // Signal completion
                .concatWithValues(Map.of("type", "done"))
                // Limit rate for backpressure control
                .limitRate(64)
                .doOnSubscribe(s -> log.debug("AI stream start"))
                .doFinally(sig -> log.debug("AI stream end -> {}", sig));
    }
}