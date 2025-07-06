package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
