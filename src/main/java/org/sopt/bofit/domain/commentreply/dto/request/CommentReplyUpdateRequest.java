package org.sopt.bofit.domain.commentreply.dto.request;


import static org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant.MAX_CONTENT_LENGTH;
import static org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant.MAX_IMAGE_COUNT;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.commentreply.service.dto.request.CommentReplyUpdateCommand;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;

public record CommentReplyUpdateRequest(
    @Schema(description = "수정할 대댓글 내용", example = "좋은 댓글이네요")
    @Length(max = MAX_CONTENT_LENGTH, message = "content 의 최대 길이인 {max}을 초과했습니다.")
    String content,

    @Schema(description = "수정된 이미지 목록")
    @Size(max = MAX_IMAGE_COUNT, message = "이미지 url 의 최대 개수({max}) 를 초과했습니다.")
    @NotNull(message = "수정된 url 목록은  null 일 수 없습니다.")
    @Valid
    List<UpdateImageRequest> updatedImages,

    @Schema(description = "삭제할 이미지 ID 목록")
    @Size(max = MAX_IMAGE_COUNT, message = "이미지 url 의 최대 개수({max}) 를 초과했습니다.")
    @NotNull(message = "삭제된 url 목록은  null 일 수 없습니다.")
    @Valid
    List<@NotNull Long> deleteImageIds
) {
    public CommentReplyUpdateCommand toCommand(){
        return new CommentReplyUpdateCommand(Optional.of(content), updatedImages, deleteImageIds);
    }
}
