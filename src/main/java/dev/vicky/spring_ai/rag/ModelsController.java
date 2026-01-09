package dev.vicky.spring_ai.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for querying model information using RAG with a focus on
 * structured output.
 * It uses a vector store and a QuestionAnswerAdvisor to provide grounded
 * answers about LLM models.
 */
@RestController
public class ModelsController {

	private final ChatClient chatClient;
	private final VectorStore vectorStore;

	public ModelsController(
			@Qualifier("googleChatClient") final ChatClient chatClient,
			@Qualifier("simpleVectorStore") final VectorStore vectorStore) {
		this.chatClient = chatClient;
		this.vectorStore = vectorStore;
	}

	/**
	 * Endpoint that provides a list of models and their context windows in a
	 * structured format.
	 *
	 * @param message The user's question, defaults to asking for OpenAI models and
	 *                context windows.
	 * @return A Models object containing the requested information retrieved via
	 *         RAG.
	 */
	@GetMapping("rag/models/faq")
	public Models faq(
			@RequestParam(value = "message", defaultValue = "Give me the list of models from OpenAI along with its context window") final String message) {

		return this.chatClient.prompt()
				.user(message)
				.advisors(QuestionAnswerAdvisor.builder(vectorStore).build()) // This can also be part of chatClient
																				// building.
				.call()
				.entity(Models.class);
	}

}
