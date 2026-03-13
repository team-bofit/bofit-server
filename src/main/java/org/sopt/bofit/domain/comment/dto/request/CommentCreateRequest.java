package org.sopt.bofit.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.comment.service.dto.request.CommentCreateCommand;

import java.util.List;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.COMMENT_CONTENT_MAX_SIZE;
import static org.sopt.bofit.domain.comment.constant.CommentConstant.MAX_IMAGE_COUNT;
import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

public record CommentCreateRequest (
	@Schema(description = "댓글 내용", example = "좋은 글이네요")
	@NotBlank(message = "content 는 필수 항목입니다.")
	@Length(max = COMMENT_CONTENT_MAX_SIZE, message = "content 의 최대 길이인 {max}을 초과했습니다.")
	String content,

	@Schema(description = "이미지 key 목록. key가 존재하지 않는 경우에도 빈 리스트를 요구")
	@Size(max = MAX_IMAGE_COUNT, message = "이미지 url 의 최대 개수({max}) 를 초과했습니다.")
	@NotNull(message = "key 목록은 비어있을 수 없습니다.")
	@Valid
	List<@Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이({max}) 를 초과했습니다.") String> imageKeys
) {
	public CommentCreateCommand toCommand(){
		return new CommentCreateCommand(this.content, this.imageKeys);
	}
}
