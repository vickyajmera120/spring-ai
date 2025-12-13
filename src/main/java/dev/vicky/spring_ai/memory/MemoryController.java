package dev.vicky.spring_ai.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memory")
public class MemoryController {

	private final ChatClient chatClient;

	// Refer to the dev.vicky.spring_ai.configuration.ChatModelConfiguration.googleChatClient for memory configuration.
	public MemoryController(@Qualifier("googleChatClient") final ChatClient chatClient) {
		this.chatClient = chatClient;
	}


	@GetMapping
	public String memory(@RequestParam String message) {
		return chatClient.prompt()
				.user(message)
				.call()
				.content();
	}

}
