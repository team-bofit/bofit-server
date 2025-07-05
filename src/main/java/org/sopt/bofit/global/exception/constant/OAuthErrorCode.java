package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    KAKAO_TOKEN_REQUEST_FAILED(HttpStatus.BAD_REQUEST.value(), "카카오 토큰 요청에 실패했습니다."),
    KAKAO_USER_INFO_REQUEST_FAILED(HttpStatus.BAD_REQUEST.value(), "카카오 계정 정보가 존재하지 않습니다."),
    JWT_USER_ID_EXTRACTION_FAILED(HttpStatus.UNAUTHORIZED.value(), "JWT에서 userId 추출 실패")
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
