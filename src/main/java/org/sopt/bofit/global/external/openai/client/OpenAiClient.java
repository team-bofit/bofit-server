package org.sopt.bofit.global.external.openai.client;

import org.sopt.bofit.global.external.openai.dto.request.ChatRequestMessage;
import org.sopt.bofit.global.external.openai.dto.request.OpenAiRequest;
import org.sopt.bofit.global.external.openai.dto.response.OpenAiResponse;
import org.sopt.bofit.global.oauth.constant.HttpHeaderConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Component
public class OpenAiClient {
	public static final String REQUEST_URI = "/chat/completions";

	private final RestClient restClient;

	@Value("${openai.model}")
	private String model;

	@Value("${openai.max-tokens}")
	private int maxTokens;

	@Value("${openai.temperature}")
	private double temperature;

	public OpenAiClient(
			@Value("${openai.secret-key}") String secretKey,
			@Value("${openai.base-url}") String baseUrl
	) {
		this.restClient = RestClient.builder()
				.baseUrl(baseUrl)
				.defaultHeader(HttpHeaders.AUTHORIZATION, HttpHeaderConstants.BEARER_PREFIX + secretKey)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public String sendRequest(List<ChatRequestMessage> messages) {
		OpenAiRequest request = new OpenAiRequest(model, messages, maxTokens, temperature);

		OpenAiResponse response = restClient.post()
				.uri(REQUEST_URI)
				.body(request)
				.retrieve()
				.body(OpenAiResponse.class);

		return parseContent(Objects.requireNonNull(response));
	}

	private String parseContent(OpenAiResponse response) {
		return response.choices().get(0).message().content().trim();
	}
}
