package org.sopt.bofit.domain.oauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KaKaoTokenResponse {
    @Schema(description = "액세스 토큰")
    private String accessToken;

    @Schema(description = "토큰 타입", example = "bearer")
    private String tokenType;

    @Schema(description = "리프레쉬 토큰")
    private String refreshToken;

    @Schema(description = "토큰 만료일", example = "43199")
    private int expiresIn;

    @Schema(description = "유저 정보 scope", example = "account_email profile")
    private String scope;

    @Schema(description = "리프레쉬 토큰 만료일", example = "5184000")
    private int refreshTokenExpiresIn;
}
