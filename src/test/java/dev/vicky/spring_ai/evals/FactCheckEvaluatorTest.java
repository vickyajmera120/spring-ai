//package dev.vicky.spring_ai.evals;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
//import org.springframework.ai.document.Document;
//import org.springframework.ai.evaluation.EvaluationRequest;
//import org.springframework.ai.evaluation.EvaluationResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.ollama.OllamaContainer;
//
///**
// * Test class for evaluating the FactCheckingEvaluator component.
// * It leverages Spring Boot's testing support and uses Testcontainers
// * for integration with the Ollama service.
// */
////@SpringBootTest
////@Testcontainers
//public class FactCheckEvaluatorTest {
//
//	private static final String OLLAMA_IMAGE = "ollama/ollama:latest";
//
//	/**
//	 * Containerized Ollama instance for testing.
//	 */
//	@Container
//	@ServiceConnection(name = "spring.ai.ollama.base-url")
//	static final OllamaContainer ollama = new OllamaContainer(OLLAMA_IMAGE);
//
//	private FactCheckingEvaluator factCheckingEvaluator;
//
//	@BeforeEach
//	public void setup(@Autowired ChatClient.Builder chatClientBuilder) {
//
//		factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder).build();
//	}
//
//	@Test
//	public void testFactCheckResponse() {
//
//		String contextDoc = """
//				The effiel tower is in Paris.
//				It is designed an built by gustav Effiel's company and completed in 1989.
//				""";
//		final String aiClaim = "The Eiffel Tower was completed in 1989.";
//
//		final EvaluationRequest evaluationRequest = new EvaluationRequest(
//				"When was the Eiffel Tower completed?",
//				List.of(new Document(contextDoc)),
//				aiClaim);
//
//		final EvaluationResponse response = factCheckingEvaluator.evaluate(evaluationRequest);
//		Assertions.assertTrue(response.isPass());
//	}
//
//
//}
