package dev.vicky.spring_ai.system_prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	private final ChatClient chatClient;

	public ArticleController(@Qualifier("googleChatClient") final ChatClient chatClient) {

		this.chatClient = chatClient;
	}


	@GetMapping("/posts/new")
	public String post(@RequestParam(value = "topic", defaultValue = "JDK Virtual Thread") String topic) {
		String blogPostGuidelines = """
			Blog Post Generator Guidelines:
			
			1. Length & Purpose: Generate 500-word blog posts that inform and engage general audiences.
			
			2. Structure:
			- Introduction: Hook readers and establish the topic's relevance
			- Body: Develop 3 main points with supporting evidence and examples
			- Conclusion: Summarize key takeaways and include a call-to-action
			
			3. Content Requirements:
			- Include real-world applications or case studies
			- Incorporate relevant statistics or data points when appropriate
			- Explain benefits/implications clearly for non-experts
			
			4. Tone & Style:
			- Write in an informative yet conversational voice
			- Use accessible language while maintaining authority
			- Break up text with subheadings and short paragraphs
			
			5. Response format:
			- Deliver complete, ready to publish post with suggested title.
			""";

		return this.chatClient.prompt()
				.user(user -> {
					user.text("Write a blog post about the topic: {topic}");
					user.param("topic", topic);
				})
				.system(blogPostGuidelines)
				.call()
				.content();
	}

}
