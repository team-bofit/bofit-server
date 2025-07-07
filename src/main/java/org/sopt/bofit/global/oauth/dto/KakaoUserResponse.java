package org.sopt.bofit.global.oauth.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record KakaoUserResponse(

        @Schema(description = "식별자에 해당하는 ID", example = "12345678")
        @JsonProperty("id")
        Long oauthId,

        @Schema(description = "카카오 계정 정보")
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(

            @Schema(description = "유저 프로필 정보")
            UserProfile profile
    ) {
        public record UserProfile(
                @Schema(description = "유저가 설정한 닉네임", example = "JeongYeon")
                String nickname,

                @Schema(description = "프로필 사진 이미지 url", example = "https://")
                String profile_image_url
        ) {}
    }
}
