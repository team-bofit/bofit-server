package org.sopt.bofit.global.external.openai.client;

import java.util.List;
import java.util.Objects;

import org.sopt.bofit.global.external.openai.dto.request.OpenAiRequest;
import org.sopt.bofit.global.external.openai.dto.request.ChatRequestMessage;
import org.sopt.bofit.global.external.openai.dto.response.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OpenAiClient {
	public final static String REQUEST_URI = "/chat/completions";
	public final static String BEARER_TYPE = "Bearer ";

	private final WebClient webClient;

	@Value("${openai.model}")
	private String model;
	@Value("${openai.max-tokens}")
	private int maxTokens;
	@Value("${openai.temperature}")
	private double temperature;

	public OpenAiClient(
		WebClient.Builder webClientBuilder,
		@Value("${openai.secret-key}") String secretKey,
		@Value("${openai.base-url}") String baseUrl
	){
		this.webClient = webClientBuilder
			.baseUrl(baseUrl)
			.defaultHeader(HttpHeaders.AUTHORIZATION, BEARER_TYPE + secretKey)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

	public String sendRequest(List<ChatRequestMessage> messages) {
		OpenAiRequest request = new OpenAiRequest(model, messages, maxTokens, temperature);

		OpenAiResponse response = webClient.post()
			.uri(REQUEST_URI)
			.bodyValue(request)
			.retrieve()
			.bodyToMono(OpenAiResponse.class)
			.block();

		return parseContent(Objects.requireNonNull(response));
	}

	private String parseContent(OpenAiResponse response) {
		return response.choices().get(0).message().content().trim();
	}

}
