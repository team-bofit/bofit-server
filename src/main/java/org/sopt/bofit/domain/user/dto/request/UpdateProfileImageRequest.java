package org.sopt.bofit.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateProfileImageRequest(
        @Schema(description = "새 프로필 이미지 url(기본 이미지면 null)", example = "string")
        String newProfileImageUrl
) {
}
