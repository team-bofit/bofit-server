package org.sopt.bofit.global.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlDto(
        @Schema(description = "presigned URL")
        String presignedUrl,

        @Schema(description = "key 값")
        String key
) {}
