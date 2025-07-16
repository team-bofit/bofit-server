package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "유효하지 않은 Http 메서드입니다."),
    NOT_FOUND_PATH(NOT_FOUND.value(), "존재하지 않는 API 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 사용자입니다."),
    INVALID_JSON(HttpStatus.BAD_REQUEST.value(), "JSON 형식이 올바르지 않습니다."),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST.value(), "필수 요청 파라미터가 누락되었습니다."),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST.value(), "요청 파라미터 타입이 일치하지 않습니다."),
    MISSING_PATH_VARIABLE(HttpStatus.BAD_REQUEST.value(), "경로 변수 값이 누락되었습니다."),
    JSON_SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Enum 리스트를 JSON 문자열로 직렬화하는 데 실패했습니다."),
    JSON_DESERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "JSON 문자열을 Enum 리스트로 역직렬화하는 데 실패했습니다."),
    AUTHENTICATION_SETTING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "인증 정보 처리에 실패했습니다"),
    JWT_USER_ID_EXTRACTION_FAILED(HttpStatus.UNAUTHORIZED.value(), "JWT에서 userId 추출 실패"),
    JWT_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED.value(), "잘못된 JWT 서명입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "만료된 토큰입니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED.value(), "지원하지 않는 JWT입니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "JWT 가 존재하지 않습니다.")
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
