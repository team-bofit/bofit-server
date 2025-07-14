package org.sopt.bofit.global.exception.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode{

	UNMATCHED_COMMENT_POST(HttpStatus.BAD_REQUEST.value(), "댓글의 게시글이 일치하지 않습니다"),

	COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "본인의 댓글만 수정/삭제할 수 있습니다."),

	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 댓글입니다."),

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
