package org.sopt.bofit.global.external.openai.client;

import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.*;
import static org.sopt.bofit.global.constant.ConfigConstant.*;
import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.*;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.global.config.properties.OpenAiProperties;
import org.sopt.bofit.global.exception.custom_exception.InternalException;
import org.sopt.bofit.global.external.openai.dto.request.ChatRequestMessage;
import org.sopt.bofit.global.external.openai.dto.request.OpenAiRequest;
import org.sopt.bofit.global.external.openai.dto.response.OpenAiResponse;
import org.sopt.bofit.global.oauth.constant.HttpHeaderConstants;
import org.sopt.bofit.global.util.JsonUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class OpenAiClient {
	public static final String REQUEST_URI = "/chat/completions";

	private final RestClient restClient;
	private final OpenAiProperties properties;

	public OpenAiClient(OpenAiProperties properties) {
		this.properties = properties;
		this.restClient = RestClient.builder()
				.baseUrl(properties.baseUrl())
				.defaultHeader(HttpHeaders.AUTHORIZATION, HttpHeaderConstants.BEARER_PREFIX + properties.secretKey())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public String sendRequest(List<ChatRequestMessage> messages) {
		OpenAiRequest request = new OpenAiRequest(
			properties.model(),
			messages,
			properties.maxTokens(),
			properties.temperature()
		);

		OpenAiResponse response = restClient.post()
			.uri(REQUEST_URI)
			.body(request)
			.retrieve()
			.body(OpenAiResponse.class);

		return parseContent(Objects.requireNonNull(response));
	}

	@Retry(name = LLM_RETRY_NAME, fallbackMethod = "generateRationaleFallback")
	public ReportRationale sendReportRelationalRequest(List<ChatRequestMessage> messages) {
		try {
			String responseString = sendRequest(messages);
			return JsonUtil.parseClass(ReportRationale.class, responseString);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
		// } catch (RuntimeException e) {
			log.info("OpenAI Exception:{}", e.getMessage());
			throw new InternalException(EXTERNAL_SERVER_ERROR);
		}
	}

	public ReportRationale generateRationaleFallback(List<ChatRequestMessage> messages, Throwable t){
		return new ReportRationale(DEFAULT_RATIONALE_REASONS, DEFAULT_RATIONAL_KEYWORD_CHIPS);
	}

	private String parseContent(OpenAiResponse response) {
		return response.choices().get(0).message().content().trim();
	}
}
