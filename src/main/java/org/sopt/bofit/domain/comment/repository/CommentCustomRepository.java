package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.springframework.data.domain.Slice;

public interface CommentCustomRepository {
    Slice<MyCommentSummaryResponse> findCommentsByCursorId(Long userId, Long cursorId, int size);
}
