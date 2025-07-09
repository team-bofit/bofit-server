package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.user.dto.response.MyCommentsResponse;
import org.springframework.data.domain.Slice;

public interface CommentCustomRepository {
    Slice<MyCommentsResponse> findCommentsByCursorId(Long userId, Long cursorId, int size);
}
