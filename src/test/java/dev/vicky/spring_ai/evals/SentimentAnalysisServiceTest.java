package dev.vicky.spring_ai.evals;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SentimentAnalysisServiceTest {

	@Autowired
	private SentimentAnalysisService sentimentAnalysisService;


	@Test
	void testPositiveSentiment() {
		final String message = "I am happy today!";
		assertEquals(Sentiment.POSITIVE, sentimentAnalysisService.sentimentAnalysis(message));
	}

	@Test
	public void testNegativeSentiment() {
		final String message = "I am sad today!";
		assertEquals(Sentiment.NEGATIVE, sentimentAnalysisService.sentimentAnalysis(message));
	}

	@Test
	public void testNeutralSentiment() {
		final String message = "I am neutral today!";
		assertEquals(Sentiment.NEUTRAL, sentimentAnalysisService.sentimentAnalysis(message));
	}

}