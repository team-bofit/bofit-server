package org.sopt.bofit.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateImageRequest(
        @Schema(description = "수정할 이미지 ID")
        @NotNull
        Long id,

        @Schema(description = "새 이미지 URL (변경 없으면 null)")
        String newImageUrl,

        @Schema(description = "새 순서, 1부터 시작")
        @Positive
        Integer newSequence
) {
}
