package org.sopt.bofit.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateProfileRequest(

        @Schema(description = "닉네임", example = "김재헌")
        String nickname,

        @Schema(description = "프로필 이미지 url")
        String profileImageUrl

) {

}
