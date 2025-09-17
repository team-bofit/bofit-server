package org.sopt.bofit.domain.commentreply.repository;

import java.util.Optional;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReplyJpaRepository extends JpaRepository<CommentReply, Long> {
    Optional<CommentReply> findByIdAndStatus(Long id, CommentReplyStatus status);
}
