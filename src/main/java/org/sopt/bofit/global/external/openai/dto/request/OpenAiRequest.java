package org.sopt.bofit.global.external.openai.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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