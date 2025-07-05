package org.sopt.bofit.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KaKaoTokenResponse {
    @Schema(description = "액세스 토큰")
    private String access_token;

    @Schema(description = "토큰 타입", example = "bearer")
    private String token_type;

    @Schema(description = "리프레쉬 토큰")
    private String refresh_token;

    @Schema(description = "토큰 만료일", example = "43199")
    private int expires_in;

    @Schema(description = "유저 정보 scope", example = "account_email profile")
    private String scope;

    @Schema(description = "리프레쉬 토큰 만료일", example = "5184000")
    private int refresh_token_expires_in;
}
