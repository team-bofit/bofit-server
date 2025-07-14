package org.sopt.bofit.domain.comment.repository;

import java.util.Optional;

import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.springframework.data.domain.Slice;

public interface CommentCustomRepository {
    Slice<MyCommentSummaryResponse> findCommentsByCursorId(Long userId, Long cursorId, int size);
    Slice<CommentResponse> findActivesByPostIdWithCursor(Long postId, Optional<Long> cursor, int size);
}
