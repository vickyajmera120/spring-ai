package dev.vicky.spring_ai.ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for interacting with locally hosted models via Ollama.
 */
@RestController
@RequestMapping("/ollama")
public class LocalModelController {

    private final ChatClient chatClient;

    public LocalModelController(@Qualifier("ollamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Sends a prompt to the local Ollama model and returns the response.
     *
     * @param prompt The user's question or instruction.
     * @return The response from the local LLM.
     */
    @RequestMapping("/ask")
    public String ask(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
