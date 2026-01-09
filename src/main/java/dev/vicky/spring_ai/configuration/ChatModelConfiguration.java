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

/**
 * Configuration class for setting up various ChatClient beans.
 * Defines clients for OpenAI, Google Gemini, Ollama, and specialized sentiment
 * analysis.
 */
@Configuration
public class ChatModelConfiguration {

	/**
	 * Configures a ChatClient.Builder using the default OpenAI model.
	 *
	 * @param model The OpenAiChatModel to use.
	 * @return A builder for creating ChatClient instances.
	 */
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

	/**
	 * Configures a ChatClient for Google Gemini with conversational memory
	 * capabilities.
	 *
	 * @param googleGenAiChatModel The Google GenAI model.
	 * @param chatMemory           The memory implementation to use for sessions.
	 * @return A memory-aware ChatClient.
	 */
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
