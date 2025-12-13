package dev.vicky.spring_ai.structured_output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vacation")
public class VacationController {

	private final ChatClient chatClient;

	public VacationController(@Qualifier("googleChatClient") final ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/unstructured")
	public String unstructured() {

		return this.chatClient.prompt()
				.user("I wanna plan a trip to Dubai. Please provide me a list of interesting things to do")
				.call()
				.content();
	}


	@GetMapping("/structured")
	public Itinerary structured() {

		return this.chatClient.prompt()
				.user("I wanna plan a trip to Dubai. Please provide me a list of interesting things to do")
				.call()
				.entity(Itinerary.class);
	}


}
