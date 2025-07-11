package org.sopt.bofit.global.external.openai.dto.response;

import java.util.List;

public record OpenAiResponse (
	List<Choice> choices
) {

	public record Choice (
		int index,
		ResponseMessage message
	){
	}
}
