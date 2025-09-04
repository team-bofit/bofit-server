package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum S3ErrorCode implements ErrorCode {
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.BAD_REQUEST.value(), "지원하지 않는 확장자 형식입니다."),
    UNSUPPORTED_IMAGE_TYPE(HttpStatus.BAD_REQUEST.value(), "이미지는 .jpg, .jpeg, .png, .webp 형식만 지원합니다.")
    ;

    private final int httpStatus;
    private final String message;

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
