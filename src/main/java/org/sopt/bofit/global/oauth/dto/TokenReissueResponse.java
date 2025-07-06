package org.sopt.bofit.global.oauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenReissueResponse(
        @Schema(description = "액세스 토큰")
        String accessToken,

        @Schema(description = "리프레쉬 토큰")
        String refreshToken
) {
    public static TokenReissueResponse of(String accessToken, String refreshToken) {
        return new TokenReissueResponse(accessToken, refreshToken);
    }
}
