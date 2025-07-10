package org.sopt.bofit.global.oauth.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record KaKaoLoginResponse(
        @Schema(description = "유저 PK", example = "1")
        Long userId,

        @Schema(description = "액세스 토큰")
        String accessToken,

        @Schema(description = "리프레쉬 토큰")
        String refreshToken
)
{
    public static KaKaoLoginResponse of(Long userId, String accessToken, String refreshToken) {
        return new KaKaoLoginResponse(userId, accessToken, refreshToken);
    }
}
