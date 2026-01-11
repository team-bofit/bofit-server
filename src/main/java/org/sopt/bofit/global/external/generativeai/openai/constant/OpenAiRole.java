package org.sopt.bofit.global.external.generativeai.openai.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OpenAiRole {
	USER("user"),
	SYSTEM("developer")
	;
	private final String value;
}
