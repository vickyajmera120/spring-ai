package dev.vicky.spring_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ChatController {

	private final ChatClient chatClient;

	public ChatController(@Qualifier("googleChatClient") final ChatClient chatClient) {

		this.chatClient = chatClient;
	}


	@GetMapping("/chat")
	public String chat() {
		return this.chatClient.prompt()
				.user("Tell me something about Java")
				.call()
				.content();
	}

	@GetMapping(value = "/stream")
	public Flux<String> stream() {
		return this.chatClient.prompt()
				.user("I am visiting Himachal next year. Please tell me 10 interesting places to visit and enjoy the trip.")
				.stream()
				.content();
	}

	@GetMapping("/joke")
	public ChatResponse chatResponse() {
		return this.chatClient.prompt()
				.user("Tell me a good joke !")
				.call()
				.chatResponse();
	}

}
