package dev.vicky.spring_ai.tools.datetime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateTimeController {

	private final ChatClient chatClient;

	public DateTimeController(@Qualifier("googleChatClient") final ChatClient chatClient) {

		this.chatClient = chatClient;
	}

	@GetMapping("/tools")
	public String tools() {
		return this.chatClient.prompt()
				.user("What is tomorrow's date in user's timezone?")
				.tools(new DateTimeTools())
				.call()
				.content();
	}


}
