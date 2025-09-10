package org.sopt.bofit.domain.comment.dto.request;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.COMMENT_CONTENT_MAX_SIZE;
import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.comment.service.dto.request.CommentCreateCommand;

public record CommentCreateRequest (
	@Schema(description = "댓글 내용", example = "좋은 글이네요")
	@NotBlank(message = "content 는 필수 항목입니다.")
	@Length(max = COMMENT_CONTENT_MAX_SIZE, message = "content 의 최대 길이인 {max}을 초과했습니다.")
	String content,

	@Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이 ({max}) 를 초과했습니다.")
	String imageUrl
) {
	public CommentCreateCommand toCommand(){
		return new CommentCreateCommand(this.content, Optional.of(this.imageUrl));
	}
}
