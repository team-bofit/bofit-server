package org.sopt.bofit.domain.comment.dto.request;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.*;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest (
	@Schema(description = "댓글 내용", example = "좋은 글이네요")
	@NotBlank(message = "content 는 필수 항목입니다.")
	@Length(max = COMMENT_CONTENT_MAX_SIZE, message = "content 의 최대 길이인 {max}을 초과했습니다.")
	String content
) {
}
