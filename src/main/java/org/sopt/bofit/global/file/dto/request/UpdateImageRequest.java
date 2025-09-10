package org.sopt.bofit.global.file.dto.request;

import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record UpdateImageRequest(
        @Schema(description = "수정할 이미지 ID")
        @NotNull
        Long id,

        @Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이를 초과했습니다.")
        @Schema(description = "새 이미지 URL (변경 없으면 null)")
        String newImageUrl,

        @Schema(description = "새 순서, 1부터 시작")
        @Positive
        Integer newSequence
) {
}
