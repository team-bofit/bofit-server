package org.sopt.bofit.domain.oauth.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KakaoUserResponse {
    @Schema(description = "식별자에 해당하는 ID", example = "12345678")
    private Long oauthId;

    @Schema(description = "카카오 계정 정보")
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {

        @Schema(description = "이름", example = "이정연")
        private String name;

        @Schema(description = "성별", example = "male")
        private String gender;

        @Schema(description = "생일", example = "1112")
        private String birthday;

        @Schema(description = "출생연도", example = "2000")
        private String birthyear;

        @Schema(description = "유저 프로필 정보")
        private UserProfile profile;

        @Getter
        public static class UserProfile {
            @Schema(description = "유저가 설정한 닉네임", example = "JeongYeon")
            private String nickname;

            @Schema(description = "프로필 사진 이미지 url", example = "https://")
            private String profile_image_url;
        }
    }
}
