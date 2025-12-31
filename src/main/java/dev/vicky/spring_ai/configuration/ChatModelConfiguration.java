package dev.vicky.spring_ai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatModelConfiguration {


	@Bean
	public ChatClient openAiChatClient(final OpenAiChatModel openAiChatModel) {
		return ChatClient.create(openAiChatModel);
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
