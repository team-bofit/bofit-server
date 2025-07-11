package org.sopt.bofit.global.external.openai.template;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiPromptTemplate {
	private static final String BASE_SYSTEM_MESSAGE = "JSON 파싱을 위해 무조건 아래에 제시된 응답 값 형식을 지킬 것.";
	private static final String BASE_REQUEST_MESSAGE =
            """
            ### 요청하고 싶은 것
            {{request}}
                    
            ### 응답 값 형식 - 바로 parsing 할 수 있도록 아래 형식을 절대적으로 준수할 것.
            {{responseFormat}}
            """;

	public static String createDefaultMessage(String request, String responseFormat) {
		return BASE_REQUEST_MESSAGE.
			replace("{{request}}", request)
			.replace("{{responseFormat}}", responseFormat);
	}

	public static String createSystemMessage(String request) {
		return BASE_SYSTEM_MESSAGE + request;
	}
}
