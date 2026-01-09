package dev.vicky.spring_ai.document_chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling chat requests using context from indexed documents.
 * This class interacts with a vector store to retrieve relevant document chunks
 * and uses a chat client to generate responses based on that context.
 */
@RestController
@Slf4j
public class DocumentChatController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    // We inject the 'docVectorStore' bean we created in your config
    public DocumentChatController(@Qualifier("ollamaChatClient") ChatClient chatClient,
            @Qualifier("docVectorStore") VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    /**
     * Handles chat requests by searching for relevant information in the document
     * vector store.
     * It uses the retrieved document chunks as context for the LLM to provide
     * grounded answers.
     *
     * @param query The user's question or prompt.
     * @return The AI-generated response based on the document context, or a default
     *         message if no context is found.
     */
    @GetMapping("/chat/doc")
    public String chatWithDocs(@RequestParam String query) {
        // 1. Increase topK to 5 to get more context
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(5) // Get more pieces of the puzzle
                        .similarityThreshold(0.5) // Optional: ignore non-relevant stuff
                        .build());

        // 2. Log what was found (Debugging is key!)
        log.info("Found {} relevant chunks for query: {}", similarDocuments.size(), query);
        similarDocuments.forEach(d -> log.info("Chunk Source: {} | Content: {}", d.getMetadata().get("filename"),
                d.getText().substring(0, Math.min(50, d.getText().length()))));

        if (similarDocuments.isEmpty()) {
            return "I couldn't find any information about that in your documents.";
        }

        String context = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        // 3. Improve the System Prompt to force use of Context
        final String systemPrompt = """
                You are a technical assistant for OpsHub.
                Use ONLY the provided CONTEXT to answer the question.
                If the answer is not in the context, say "I don't have information on that in my current documents."

                CONTEXT:
                {context}
                """;

        return chatClient.prompt()
                .system(s -> s.text(systemPrompt).param("context", context))
                .user(query)
                .call()
                .content();
    }
}