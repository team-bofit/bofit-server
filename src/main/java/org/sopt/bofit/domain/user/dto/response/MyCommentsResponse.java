package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MyCommentsResponse(

        @Schema(description = "댓글 ID")
        Long commentId,

        @Schema(description = "댓글 내용", example = "저도요")
        String content,

        @Schema(description = "작성 시간")
        LocalDateTime createdAt
) {
    public static MyCommentsResponse of(Long commentId, String content, LocalDateTime createdAt) {
        return new MyCommentsResponse(commentId, content, createdAt);
    }
}
