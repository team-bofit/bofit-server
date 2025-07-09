package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.user.dto.response.CommentSummaryResponse;
import org.springframework.data.domain.Slice;

public interface CommentCustomRepository {
    Slice<CommentSummaryResponse> findCommentsByCursorId(Long userId, Long cursorId, int size);
}
