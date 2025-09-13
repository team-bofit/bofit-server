package org.sopt.bofit.global.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode{

	UNMATCHED_COMMENT_POST(HttpStatus.BAD_REQUEST.value(), "댓글의 게시글이 일치하지 않습니다"),
    COMMENT_IMAGE_EXCEED(HttpStatus.BAD_REQUEST.value(), "댓글의 이미지 개수를 초과했습니다."),
    UNMATCHED_COMMENT_IMAGE(HttpStatus.BAD_REQUEST.value(), "요청한 댓글과 이미지가 일치하지 않습니다."),

	COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "본인의 댓글만 수정/삭제할 수 있습니다."),

	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 댓글입니다."),

	COMMENT_ALREADY_DELETED(HttpStatus.CONFLICT.value(), "이미 삭제된 댓글입니다."),

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
