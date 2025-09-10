package org.sopt.bofit.domain.commentreply.dto.request;

import static org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant.MAX_CONTENT_LENGTH;
import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.commentreply.service.dto.request.CommentReplyCreateCommand;

public record CommentReplyRequest (
    @Schema(description = "본문", example = "대댓글 작성하기 ~")
    @NotBlank(message = "본문은 비어있을 수 없습니다.")
    @Length(max = MAX_CONTENT_LENGTH, message = "대댓글의 최대 길이 ({max}) 를 초과했습니다.")
    String content,

    @Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이 ({max}) 를 초과했습니다.")
    String imageUrl
){
    public CommentReplyCreateCommand toCommand(){
        return new CommentReplyCreateCommand(this.content, Optional.of(this.imageUrl));
    }

}
