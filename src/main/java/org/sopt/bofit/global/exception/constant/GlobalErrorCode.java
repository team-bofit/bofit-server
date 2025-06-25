package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "유효하지 않은 Http 메서드입니다."),
    NOT_FOUND_PATH(NOT_FOUND.value(), "존재하지 않는 API 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다."),
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
