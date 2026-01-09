package dev.vicky.spring_ai.structured_output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller demonstrating structured vs unstructured output for vacation
 * planning.
 */
@RestController
@RequestMapping("vacation")
public class VacationController {

	private final ChatClient chatClient;

	public VacationController(@Qualifier("googleChatClient") final ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	/**
	 * Endpoint providing an unstructured list of things to do in Dubai.
	 *
	 * @return A plain text response from the LLM.
	 */
	@GetMapping("/unstructured")
	public String unstructured() {

		return this.chatClient.prompt()
				.user("I wanna plan a trip to Dubai. Please provide me a list of interesting things to do")
				.call()
				.content();
	}

	/**
	 * Endpoint providing a structured itinerary for a trip to Dubai.
	 * This uses the entity mapping feature to return a strongly-typed Java object.
	 *
	 * @return An Itinerary object containing things to do in Dubai.
	 */
	@GetMapping("/structured")
	public Itinerary structured() {

		return this.chatClient.prompt()
				.user("I wanna plan a trip to Dubai. Please provide me a list of interesting things to do")
				.call()
				.entity(Itinerary.class);
	}

}
