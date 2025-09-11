package org.sopt.bofit.domain.comment.repository;

import java.util.Optional;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends CommentCustomRepository, CommentJpaRepository {

    Optional<Comment> findByIdAndStatus(Long id, CommentStatus commentStatus);
}
