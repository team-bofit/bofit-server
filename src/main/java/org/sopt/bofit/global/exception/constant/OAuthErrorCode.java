package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    KAKAO_TOKEN_REQUEST_FAILED(HttpStatus.BAD_REQUEST.value(), "카카오 토큰 요청에 실패했습니다."),
    KAKAO_USER_INFO_REQUEST_FAILED(HttpStatus.BAD_REQUEST.value(), "카카오 계정 정보가 존재하지 않습니다."),
    JWT_REFRESH_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "RefreshToken 정보가 존재하지 않습니다."),
    JWT_REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED.value(), "RefreshToken이 일치하지 않습니다.")
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
