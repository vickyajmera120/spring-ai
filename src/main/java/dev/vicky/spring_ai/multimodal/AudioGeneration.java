package dev.vicky.spring_ai.multimodal;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi.SpeechRequest.AudioResponseFormat;
import org.springframework.ai.openai.api.OpenAiAudioApi.SpeechRequest.Voice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioGeneration {

	private final OpenAiAudioSpeechModel speechModel;


	public AudioGeneration(final OpenAiAudioSpeechModel speechModel) {
		this.speechModel = speechModel;
	}

	@GetMapping("/speak")
	public ResponseEntity<byte[]> getAudio(@RequestParam("prompt") final String prompt) {

		final OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
				.model("tts-1-hd")
				.voice(Voice.ALLOY)
				.responseFormat(AudioResponseFormat.MP3)
				.speed(1.0)
				.build();

		final TextToSpeechPrompt textToSpeechPrompt = new TextToSpeechPrompt(prompt, options);
		final TextToSpeechResponse response = speechModel.call(textToSpeechPrompt);

		final byte[] output = response.getResult().getOutput();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audio.mp3")
				.body(output);
	}

}
