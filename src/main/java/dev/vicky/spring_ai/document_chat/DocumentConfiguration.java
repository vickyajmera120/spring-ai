package dev.vicky.spring_ai.document_chat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for document processing and vector store setup.
 * Handles loading, splitting, and indexing text documents into a vector store.
 */
@Slf4j
@Configuration
public class DocumentConfiguration {

	@Value("classpath:/documents/*.txt")
	private Resource[] textFiles;

	/**
	 * Configures a SimpleVectorStore for document retrieval.
	 * Loads an existing vector store from disk if available, otherwise creates one
	 * from classpath documents.
	 *
	 * @param embeddingModel The model used for generating document embeddings.
	 * @return A SimpleVectorStore containing indexed documents.
	 */
	@Bean
	public SimpleVectorStore docVectorStore(@Qualifier("ollamaEmbeddingModel") final EmbeddingModel embeddingModel) {

		final SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
		final File vectorStoreFile = getVectorStoreFile();

		if (vectorStoreFile.exists()) {
			log.info("Loading vector store from {}", vectorStoreFile.getAbsolutePath());
			vectorStore.load(vectorStoreFile);
		} else {
			log.info("Creating vector store at {}", vectorStoreFile.getAbsolutePath());

			// Create a list to hold documents from ALL files
			final List<Document> allDocuments = new ArrayList<>();

			// Loop through each file resource
			for (Resource file : textFiles) {
				log.info("Processing file: {}", file.getFilename());

				TextReader textReader = new TextReader(file);

				// Optional: Store the filename in metadata so you know where the answer came
				// from later
				textReader.getCustomMetadata().put("filename", file.getFilename());

				allDocuments.addAll(textReader.get());
			}

			final TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
			// Split all collected documents
			final List<Document> splitDocuments = tokenTextSplitter.apply(allDocuments);

			log.info("Adding {} split documents to vector store", splitDocuments.size());
			vectorStore.add(splitDocuments);
			vectorStore.save(vectorStoreFile);
		}

		return vectorStore;
	}

	private File getVectorStoreFile() {
		final Path path = Paths.get("src", "main", "resources", "vector_store");
		// Ensure the directory exists
		File directory = path.toFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}
		final String vectorStorePath = directory.getAbsolutePath() + "/" + "vectorStore.json";
		return new File(vectorStorePath);
	}

}