package org.sopt.bofit.global.file.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record NewImageRequest(
        @Schema(description = "이미지 URL")
        @NotBlank
        String imageUrl,

        @Schema(description = "순서 (null이면 맨 뒤에 추가, 1부터 시작)", example = "3")
        @Positive
        Integer sequence
) {
}
