package org.sopt.bofit.global.file.dto.response;

import java.util.List;

public record PresignedUrlResponse(
        List<PresignedUrlDto> presignedUrlDtos
) {
    public static PresignedUrlResponse of(List<PresignedUrlDto> presignedUrlDtos) {
        return new PresignedUrlResponse(presignedUrlDtos);
    }
}
