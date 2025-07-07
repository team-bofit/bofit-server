package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileResponse(
        @Schema(description = "유저 PK", example = "1")
        Long userId,

        @Schema(description = "유저 이름", example = "이정연")
        String userName,

        @Schema(description = "유저 닉네임", example = "장정훈")
        String nickname,

        @Schema(description = "유저 프로필 사진 url")
        String profileImageUrl,

        @Schema(description = "보험 추천 받았는지 여부")
        boolean isRecommendInsurance
) {

    public static UserProfileResponse of(Long userId, String userName, String nickname, String profileImageUrl, boolean isRecommendInsurance) {
        return new UserProfileResponse(userId, userName, nickname, profileImageUrl, isRecommendInsurance);
    }

}
