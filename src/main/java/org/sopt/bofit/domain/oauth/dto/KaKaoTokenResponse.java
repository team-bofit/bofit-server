package org.sopt.bofit.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


public record KaKaoTokenResponse(
        @Schema(description = "액세스 토큰")
        String access_token,

        @Schema(description = "토큰 타입", example = "bearer")
        String token_type,

        @Schema(description = "리프레쉬 토큰")
        String refresh_token,

        @Schema(description = "토큰 만료일", example = "43199")
        int expires_in,

        @Schema(description = "유저 정보 scope", example = "account_email profile")
        String scope,

        @Schema(description = "리프레쉬 토큰 만료일", example = "5184000")
        int refresh_token_expires_in

) {

}
