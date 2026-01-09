package dev.vicky.spring_ai.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller demonstrating conversational memory capabilities.
 * It uses a chat client configured with memory advisors to maintain context
 * across requests.
 */
@RestController
@RequestMapping("/memory")
public class MemoryController {

	private final ChatClient chatClient;

	// Refer to the
	// dev.vicky.spring_ai.configuration.ChatModelConfiguration.googleChatClient for
	// memory configuration.
	public MemoryController(@Qualifier("googleChatClient") final ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	/**
	 * Endpoint for chatting with memory. The conversation context is preserved
	 * internally.
	 *
	 * @param message The message sent by the user.
	 * @return The LLM's response, considering the previous interaction history.
	 */
	@GetMapping
	public String memory(@RequestParam String message) {
		return chatClient.prompt()
				.user(message)
				.call()
				.content();
	}

}
