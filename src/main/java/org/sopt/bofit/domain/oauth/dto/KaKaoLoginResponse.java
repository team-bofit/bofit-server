package org.sopt.bofit.domain.oauth.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record KaKaoLoginResponse(
        @Schema(description = "유저 PK", example = "1")
        Long userId,

        @Schema(description = "첫 접속 여부(서비스 고지사항 노출용)", example = "true")
        boolean isRegistered
)
{
    public static KaKaoLoginResponse of(Long userId, boolean isRegistered) {
        return new KaKaoLoginResponse(userId, isRegistered);
    }
}
