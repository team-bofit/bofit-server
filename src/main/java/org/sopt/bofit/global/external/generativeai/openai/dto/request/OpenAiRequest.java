package org.sopt.bofit.global.external.generativeai.openai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class OpenAiRequest {
	private String model;
	private List<ChatRequestMessage> messages;
	@JsonProperty("max_tokens")
	private int maxTokens;
	private double temperature;
}