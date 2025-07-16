package org.sopt.bofit.global.oauth.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathConstant {
	public static final String[] ALLOWED_PATHS = {
		"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
		"/oauth/kakao/login", "/actuator/health"
	};
}
