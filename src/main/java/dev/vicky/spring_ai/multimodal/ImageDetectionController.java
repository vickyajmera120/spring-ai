package dev.vicky.spring_ai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetectionController {

	private final ChatClient chatClient;

	@Value("classpath:images/sample 2.jpeg")
	private Resource image;

	public ImageDetectionController(@Qualifier("googleChatClient") ChatClient chatClient) {
		this.chatClient = chatClient;
	}


	@GetMapping("/image-to-text")
	public String imageToText() {

		return chatClient.prompt()
				.user(u -> {
					u.text("Can you please determine what's there in the image?");
					u.media(MimeTypeUtils.IMAGE_JPEG, image);
				})
				.call()
				.content();

	}

}
