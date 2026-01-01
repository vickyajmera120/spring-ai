package dev.vicky.spring_ai.evals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RelevancyEvaluatorTest {

	private RelevancyEvaluator relevancyEvaluator;

	@BeforeEach
	public void setup(@Autowired ChatClient.Builder chatClientBuilder) {
		relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
	}

	@Test
	public void testRelevancyResponse() {

		final String userQuery = "What is the capital of France?";
		List<String> context = List.of(
				"France is the country in Western Europe.",
				"The capital of France is Paris which is also the largest city.",
				"The Eiffel Tower is in Paris.");

		final String llmResponse = "The capital of France is Paris.";

		final EvaluationRequest evaluationRequest = new EvaluationRequest(userQuery, contextStringsToDocs(context), llmResponse);
		final EvaluationResponse response = relevancyEvaluator.evaluate(evaluationRequest);
		Assertions.assertTrue(response.isPass());
	}


	@Test
	public void testRelevancyResponseNegative() {

		final String userQuery = "What is the capital of France?";
		List<String> context = List.of(
				"France is the country in Western Europe.",
				"The capital of France is Paris which is also the largest city.",
				"The Eiffel Tower is in Paris.");

		final String llmResponse = "I enjoy eating pizza.";

		final EvaluationRequest evaluationRequest = new EvaluationRequest(userQuery, contextStringsToDocs(context), llmResponse);
		final EvaluationResponse response = relevancyEvaluator.evaluate(evaluationRequest);
		Assertions.assertFalse(response.isPass());
	}

	private List<Document> contextStringsToDocs(final List<String> context) {

		return context.stream()
				.map(Document::new)
				.toList();
	}

}
