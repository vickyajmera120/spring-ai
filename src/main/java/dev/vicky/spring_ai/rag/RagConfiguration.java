package dev.vicky.spring_ai.rag;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for RAG (Retrieval-Augmented Generation) setups.
 * Sets up a simple vector store for model-related information.
 */
@Slf4j
@Configuration
public class RagConfiguration {

	@Value("classpath:/data/models.json")
	private Resource models;

	/**
	 * Configures a SimpleVectorStore for general RAG information.
	 *
	 * @param embeddingModel The model used for generating embeddings.
	 * @return A SimpleVectorStore initialized with model data.
	 */
	@Bean
	public SimpleVectorStore simpleVectorStore(final OpenAiEmbeddingModel embeddingModel) {

		final SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
		final File vectorStoreFile = getVectorStoreFile();

		if (vectorStoreFile.exists()) {
			log.info("Loading vector store from {}", vectorStoreFile.getAbsolutePath());
			vectorStore.load(vectorStoreFile);
		} else {
			log.info("Creating vector store at {}", vectorStoreFile.getAbsolutePath());
			final TextReader textReader = new TextReader(models);
			textReader.getCustomMetadata().put("fileName", "models.txt");
			final List<Document> documents = textReader.get();
			final TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
			final List<Document> splitDocuments = tokenTextSplitter.apply(documents);
			vectorStore.add(splitDocuments);
			vectorStore.save(vectorStoreFile);
		}

		return vectorStore;
	}

	private File getVectorStoreFile() {

		final Path path = Paths.get("src", "main", "resources", "data");
		final String vectorStorePath = path.toFile().getAbsolutePath() + "/" + "vectorStore.json";
		return new File(vectorStorePath);
	}

}