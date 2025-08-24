package org.sopt.bofit.global.oauth.dto.request;

import jakarta.validation.constraints.NotNull;

public record OAuthLoginRequest (
	@NotNull
	String code,
	String redirectUrl
){
}
