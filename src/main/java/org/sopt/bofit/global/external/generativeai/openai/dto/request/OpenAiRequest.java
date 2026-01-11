package org.sopt.bofit.global.external.generativeai.openai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * reasoningEffort: 추론 정도 조절. minimal, low, medium, high
 */
@Getter
@AllArgsConstructor
@ToString
public class OpenAiRequest {
	private String model;
	private List<ChatRequestMessage> messages;
	@JsonProperty("max_completion_tokens")
	private int maxTokens;
	private double temperature;
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;
    @JsonProperty("reasoning_effort")
    private String reasoningEffort;
}