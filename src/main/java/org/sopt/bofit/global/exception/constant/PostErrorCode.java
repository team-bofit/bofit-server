package org.sopt.bofit.global.exception.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {
    POST_TITLE_BLANK(HttpStatus.BAD_REQUEST.value(), "제목은 비어있을 수 없습니다."),
    POST_CONTENT_BLANK(HttpStatus.BAD_REQUEST.value(), "본문은 비어있을 수 없습니다."),
    POST_TITLE_LONG(HttpStatus.BAD_REQUEST.value(), "제목은 30자 미만으로 입력해주세요."),
    POST_CONTENT_LONG(HttpStatus.BAD_REQUEST.value(), "내용은 3000자 미만으로 작성해주세요"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 게시글입니다."),
    POST_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "본인의 게시글만 삭제할 수 있습니다.")

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
