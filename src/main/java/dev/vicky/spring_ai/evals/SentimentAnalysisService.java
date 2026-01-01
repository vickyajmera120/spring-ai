package dev.vicky.spring_ai.evals;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

	private final ChatClient chatClient;

	public SentimentAnalysisService(final @Qualifier("sentimentChatClient") ChatClient chatClient) {

		this.chatClient = chatClient;
	}

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
