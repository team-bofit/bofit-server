package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentSummaryResponse(

        @Schema(description = "댓글 ID")
        Long commentId,

        @Schema(description = "댓글을 작성한 게시글 ID")
        Long postId,

        @Schema(description = "댓글 내용", example = "저도요")
        String content,

        @Schema(description = "작성 시간")
        LocalDateTime createdAt
) {
    public static CommentSummaryResponse of(Long commentId, Long postId, String content, LocalDateTime createdAt) {
        return new CommentSummaryResponse(commentId, postId, content, createdAt);
    }
}
