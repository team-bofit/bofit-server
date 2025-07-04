package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    KAKAO_TOKEN_REQUEST_FAILED(400, "카카오 토큰 요청에 실패했습니다.")
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
