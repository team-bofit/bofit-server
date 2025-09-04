package org.sopt.bofit.global.s3.dto.response;

import java.util.List;

public record PresignedUrlResponse(
        List<String> presignedUrls
) {
    public static PresignedUrlResponse of(List<String> urls){
        return new PresignedUrlResponse(urls);
    }
}
