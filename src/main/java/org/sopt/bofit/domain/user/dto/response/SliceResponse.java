package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        @Schema(description = "데이터 목록") List<T> content,
        @Schema(description = "다음 커서 ID") Long nextCursorId,
        @Schema(description = "마지막 페이지 여부") boolean isLast
) {
    public static <T> SliceResponse<T> of(Slice<T> slice) {
        List<T> content = slice.getContent();
        Long nextCursorId = null;

        if (!slice.isLast() && !content.isEmpty()) {
            Object lastElement = content.get(content.size() - 1);
            if (lastElement instanceof MyPostsResponse lastPost) {
                nextCursorId = lastPost.postId();
            }
            else if (lastElement instanceof MyCommentsResponse lastComment) {
                nextCursorId = lastComment.commentId();
            }
        }

        return new SliceResponse<>(content, nextCursorId, slice.isLast());
    }
}
