package org.sopt.bofit.global.external.generativeai.openai.template;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiPromptTemplate {
	private static final String BASE_SYSTEM_MESSAGE = "JSON 파싱을 위해 무조건 아래에 제시된 응답 값 형식을 지킬 것.";

    private static final String BASE_REQUEST_MESSAGE =
        """
        ### 요청하고 싶은 것
        {{request}}
        """;

    public static String createDefaultMessage(String request) {
        return BASE_REQUEST_MESSAGE.
            replace("{{request}}", request);
    }

	public static String createSystemMessage(String request) {
		return BASE_SYSTEM_MESSAGE + request;
	}
}
