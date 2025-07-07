package org.sopt.bofit.global.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


public record KaKaoTokenResponse(
        @Schema(description = "액세스 토큰")
        @JsonProperty("access_token")
        String accessToken,

        @Schema(description = "토큰 타입", example = "bearer")
        @JsonProperty("token_type")
        String tokenType,

        @Schema(description = "리프레쉬 토큰")
        @JsonProperty("refresh_token")
        String refreshToken,

        @Schema(description = "토큰 만료일", example = "43199")
        @JsonProperty("expires_in")
        int expiresIn,

        @Schema(description = "유저 정보 scope", example = "account_email profile")
        String scope,

        @Schema(description = "리프레쉬 토큰 만료일", example = "5184000")
        @JsonProperty("refresh_token_expires_in")
        int refreshTokenExpiresIn

) {

}
