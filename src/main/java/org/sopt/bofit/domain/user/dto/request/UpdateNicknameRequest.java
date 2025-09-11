package org.sopt.bofit.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateNicknameRequest(
        @Schema(description = "새 닉네임", example = "정연")
        String newNickname
) {
}
