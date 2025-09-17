package org.sopt.bofit.domain.commentreply.repository;

import java.util.Optional;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.commentreply.service.dto.response.CommentReplyResponse;
import org.springframework.data.domain.Slice;

public interface CommentReplyCustomRepository {
    Slice<CommentReplyResponse> findActivesByComment(Comment targetComment, Optional<Long> cursor, int size);
}
