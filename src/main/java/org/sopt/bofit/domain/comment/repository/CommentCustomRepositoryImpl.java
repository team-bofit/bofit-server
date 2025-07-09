package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.user.dto.response.MyCommentsResponse;
import org.springframework.data.domain.Slice;

public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    @Override
    public Slice<MyCommentsResponse> findCommentsByCursorId(Long userId, Long cursorId, int size) {
        return null;
    }
}
