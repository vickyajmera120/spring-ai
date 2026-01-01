package dev.vicky.spring_ai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatModelConfiguration {


	@Bean
	public ChatClient.Builder chatClientBuilder(final OpenAiChatModel model) {
		return ChatClient.builder(model);
	}


	@Bean
	public ChatClient openAiChatClient(final OpenAiChatModel openAiChatModel) {
		return ChatClient.create(openAiChatModel);
	}

	@Bean
	public ChatClient sentimentChatClient(final OpenAiChatModel model) {
		return ChatClient.builder(model)
				.defaultOptions(OpenAiChatOptions.builder()
						.temperature(0.1)
						.build())
				.build();
	}

	@Bean
	public ChatClient googleChatClient(final GoogleGenAiChatModel googleGenAiChatModel, final ChatMemory chatMemory) {
		return ChatClient
				.builder(googleGenAiChatModel)
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
				.build();
	}

	@Bean
	public ChatClient ollamaChatClient(final OllamaChatModel ollamaChatModel) {
		return ChatClient.create(ollamaChatModel);
	}
}
