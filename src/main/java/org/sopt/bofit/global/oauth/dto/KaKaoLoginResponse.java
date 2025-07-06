package org.sopt.bofit.global.oauth.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record KaKaoLoginResponse(
        @Schema(description = "유저 PK", example = "1")
        Long userId,

        @Schema(description = "첫 접속 여부(서비스 고지사항 노출용)", example = "true")
        boolean isRegistered,

        @Schema(description = "액세스 토큰")
        String accessToken,

        @Schema(description = "리프레쉬 토큰")
        String refreshToken
)
{
    public static KaKaoLoginResponse of(Long userId, boolean isRegistered, String accessToken, String refreshToken) {
        return new KaKaoLoginResponse(userId, isRegistered, accessToken, refreshToken);
    }
}
