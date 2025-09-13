package org.sopt.bofit.global.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentReplyErrorCode implements ErrorCode{

    UNMATCHED_COMMENT_REPLY_COMMENT(HttpStatus.BAD_REQUEST.value(), "일치하지 않는 댓글과 대댓글입니다"),

    COMMENT_REPLY_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "본인의 대댓글만 수정/삭제할 수 있습니다."),

    COMMENT_REPLY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 대댓글입니다.")

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
