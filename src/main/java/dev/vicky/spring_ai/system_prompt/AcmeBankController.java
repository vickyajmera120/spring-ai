package dev.vicky.spring_ai.system_prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for AcmeBank customer service using system prompts to restrict AI
 * behavior.
 */
@RestController
@RequestMapping("/acme")
public class AcmeBankController {

	private final ChatClient chatClient;

	public AcmeBankController(@Qualifier("googleChatClient") final ChatClient googleChatClient) {
		this.chatClient = googleChatClient;
	}

	/**
	 * Endpoint for chatting with the AcmeBank assistant.
	 *
	 * @param message The user's query.
	 * @return The AI response, restricted to banking topics.
	 */
	@GetMapping("/chat")
	public String chat(@RequestParam String message) {

		final String systemInstructions = """
				You are a customer service assistant for AcmeBank.
				You can only discuss:
				- Account balance and transactions
				- Branch locations and hours
				- General banking services

				If asked about anything else, respond: "I can help with banking related queries only".
				""";

		return this.chatClient.prompt()
				.user(message)
				.system(systemInstructions)
				.call()
				.content();
	}

}
