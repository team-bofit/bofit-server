package org.sopt.bofit.domain.commentreply.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.commentreply.service.dto.request.CommentReplyCreateCommand;

import java.util.List;

import static org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant.MAX_CONTENT_LENGTH;
import static org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant.MAX_IMAGE_COUNT;
import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

public record CommentReplyCreateRequest(
    @Schema(description = "본문", example = "대댓글 작성하기 ~")
    @NotBlank(message = "본문은 비어있을 수 없습니다.")
    @Length(max = MAX_CONTENT_LENGTH, message = "대댓글의 최대 길이({max}) 를 초과했습니다.")
    String content,

    @Schema(description = "이미지 url 목록. url 이 존재하지 않는 경우에도 빈 리스트를 요구")
    @Size(max = MAX_IMAGE_COUNT, message = "이미지 url 의 최대 개수({max}) 를 초과했습니다.")
    @NotNull(message = "url 목록은 비어있을 수 없습니다.")
    @Valid
    List<@Length(max = MAX_IMAGE_URL_LENGTH, message = "이미지 url 의 최대 길이({max}) 를 초과했습니다.") String> imageKeys
){
    public CommentReplyCreateCommand toCommand(){
        return new CommentReplyCreateCommand(this.content, this.imageKeys);
    }

}
