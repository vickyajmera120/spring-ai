package dev.vicky.spring_ai.multimodal;

import java.util.Map;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageGenerationController {

    private final OpenAiImageModel imageModel;

    @Autowired
    public ImageGenerationController(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateImage(@RequestParam String prompt) {

        final OpenAiImageOptions imageOptions = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid")
                .build();

        final ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
        final ImageResponse imageResponse = imageModel.call(imagePrompt);

        final String url = imageResponse.getResult().getOutput().getUrl();

        return ResponseEntity.ok(Map.of(
                "prompt", prompt,
                "url", url
                ));
    }

}