package dev.vicky.spring_ai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {

	private final ChatClient chatClient;


	public RagController(@Qualifier("googleChatClient") final ChatClient chatClient) {
		this.chatClient = chatClient;
	}


	@GetMapping("/models")
	public String models() {
		return this.chatClient.prompt()
				.user("Can you give me list of popular LLM AI models and their current context window?")
				.call()
				.content();
	}

	@GetMapping("/models/stuff-the-prompt")
	public String modelsStuffThePrompt() {

		final String stuff = """
				If you are asked about popular LLM models, here is some information that can help you:
				[
				  {
				    "company": "OpenAI",
				    "model": "GPT-4o",
				    "context_window_size": 128000
				  },
				  {
				    "company": "OpenAI",
				    "model": "GPT-4-preview",
				    "context_window_size": 128000
				  },
				  {
				    "company": "Anthropic",
				    "model": "Claude Opus 4",
				    "context_window_size": 200000
				  },
				  {
				    "company": "Anthropic",
				    "model": "Claude Sonnet 4",
				    "context_window_size": 200000
				  },
				  {
				    "company": "Google",
				    "model": "Gemini 2.5 Pro",
				    "context_window_size": 1000000
				  },
				  {
				    "company": "Google",
				    "model": "Gemini 2.0 Pro (Exp.)",
				    "context_window_size": 2000000
				  },
				  {
				    "company": "Google",
				    "model": "Gemini 2.0 Flash",
				    "context_window_size": 1000000
				  },
				  {
				    "company": "Meta AI",
				    "model": "Llama 3.1 405B",
				    "context_window_size": 128000
				  },
				  {
				    "company": "xAI",
				    "model": "Grok 3",
				    "context_window_size": 1000000
				  },
				  {
				    "company": "Mistral AI",
				    "model": "Mistral Large 2",
				    "context_window_size": 128000
				  },
				  {
				    "company": "Alibaba Cloud",
				    "model": "Qwen 2.5 72B",
				    "context_window_size": 128000
				  },
				  {
				    "company": "DeepSeek",
				    "model": "DeepSeek R1",
				    "context_window_size": 128000
				  }
				]
				""";


		return this.chatClient.prompt()
				.user(user -> user.text(stuff + "\nCan you give me list of popular LLM AI models and their current context window?"))
				.call()
				.content();
	}

}
