package org.sopt.bofit.global.file.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PresignedUrlRequest(
        @Schema(description = "파일 형식")
        List<String> mediaType
) {
}

