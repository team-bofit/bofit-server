package org.sopt.bofit.global.file.dto.request;

import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record NewImageRequest(
        @Schema(description = "이미지 URL")
        @Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이 ({max}) 를 초과했습니다.")
        @NotBlank
        String imageUrl,

        @Schema(description = "순서 (null이면 맨 뒤에 추가, 1부터 시작)", example = "3")
        @Positive
        Integer sequence
) {
}
