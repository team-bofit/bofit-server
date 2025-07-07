package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileResponse(
        @Schema(description = "유저 PK", example = "1")
        Long userId,

        @Schema(description = "유저 이름", example = "이정연")
        String userName,

        @Schema(description = "유저 프로필 사진 url")
        String profileImageUrl
) {

    public static UserProfileResponse of(Long userId, String userName, String profileImageUrl) {
        return new UserProfileResponse(userId, userName, profileImageUrl);
    }

}
