package org.sopt.bofit.global.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        @Schema(description = "데이터 목록") List<T> content,
        @Schema(description = "다음 커서 ID") Long nextCursor,
        @Schema(description = "마지막 페이지 여부") boolean isLast
) {
    public static <T> SliceResponse<T> of(Slice<T> slice) {
        List<T> content = slice.getContent();
        Long nextCursorId = null;

        if (!slice.isLast() && !content.isEmpty()) {
            Object lastElement = content.get(content.size() - 1);
            if (lastElement instanceof MyPostSummaryResponse lastPost) {
                nextCursorId = lastPost.postId();
            }
            else if (lastElement instanceof MyCommentSummaryResponse lastComment) {
                nextCursorId = lastComment.commentId();
            }
            else if (lastElement instanceof CommentResponse lastComment){
                nextCursorId = lastComment.commentId();
            }
        }

        return new SliceResponse<>(content, nextCursorId, slice.isLast());
    }
}
