package org.sopt.bofit.global.file.dto.request;

import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record UpdateImageRequest(
        @Schema(description = "이미지 ID")
        Long id,

        @Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이 ({max}) 를 초과했습니다.")
        @Schema(description = "새 이미지 URL (변경 없으면 null)")
        String imageUrl,

        @Schema(description = "순서, 0부터 시작")
        @PositiveOrZero
        Integer sequence
) {
}
