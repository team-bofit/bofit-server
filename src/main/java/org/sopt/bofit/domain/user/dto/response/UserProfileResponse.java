package org.sopt.bofit.domain.user.dto.response;

import org.sopt.bofit.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record UserProfileResponse(
        @Schema(description = "유저 PK", example = "1")
        Long userId,

        @Schema(description = "이름", example = "이정연")
        String username,

        @Schema(description = "유저 닉네임", example = "장정훈")
        String nickname,

        @Schema(description = "유저 프로필 사진 url")
        String profileImageUrl,

        @Schema(description = "보험 추천 받았는지 여부")
        boolean isRecommendInsurance
) {

    public static UserProfileResponse of(Long userId, String username, String nickname, String profileImageUrl, boolean isRecommendInsurance) {
        return new UserProfileResponse(userId, username, nickname, profileImageUrl, isRecommendInsurance);
    }

    @Builder
    public UserProfileResponse(Long userId, String username, String nickname, String profileImageUrl,
        boolean isRecommendInsurance) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.isRecommendInsurance = isRecommendInsurance;
    }

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
            .userId(user.getId())
            .username(user.getName())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfileImage())
            .isRecommendInsurance(user.isRecommendInsurance())
            .build();
    }

}
