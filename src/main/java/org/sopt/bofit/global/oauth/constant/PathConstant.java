package org.sopt.bofit.global.oauth.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathConstant {
	public static final String[] ALLOWED_PATHS = {
		"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-config",
		"/oauth/kakao/login", "/actuator/health"
	};

	public static final String ACTUATOR_PATH = "/actuator";
	// public static final String ACTUATOR_PATH = "/actuator/health";

	public static final String OAUTH_KAKAO_LOGIN_PATH = "/oauth/kakao/login";

}
