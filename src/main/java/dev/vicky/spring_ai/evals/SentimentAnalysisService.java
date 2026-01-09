package dev.vicky.spring_ai.evals;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service for analyzing the sentiment of a given text message.
 * It uses a specialized chat client to determine if a message is POSITIVE,
 * NEGATIVE, or NEUTRAL.
 */
@Service
public class SentimentAnalysisService {

	private final ChatClient chatClient;

	public SentimentAnalysisService(final @Qualifier("sentimentChatClient") ChatClient chatClient) {

		this.chatClient = chatClient;
	}

	/**
	 * Analyzes the sentiment of the provided message.
	 *
	 * @param message The text to analyze.
	 * @return A Sentiment enum representing the result (POSITIVE, NEGATIVE, or
	 *         NEUTRAL).
	 */
	public Sentiment sentimentAnalysis(String message) {

		final String systemMessage = """
				You are a sentiment analysis assistant.
				Provide the result in only one word: POSITIVE, NEGATIVE or NEUTRAL.""";

		return chatClient.prompt()
				.system(systemMessage)
				.user(message)
				.call()
				.entity(Sentiment.class);
	}

}
