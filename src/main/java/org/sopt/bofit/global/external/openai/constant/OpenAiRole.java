package org.sopt.bofit.global.external.openai.constant;

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
