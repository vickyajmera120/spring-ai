package dev.vicky.spring_ai.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acme")
public class AcmeBankController {

	private final ChatClient chatClient;

	public AcmeBankController(@Qualifier("googleChatClient") final ChatClient googleChatClient) {
		this.chatClient = googleChatClient;
	}


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
