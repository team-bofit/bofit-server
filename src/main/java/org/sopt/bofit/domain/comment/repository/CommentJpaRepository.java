package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPostIdAndStatus(Long postId, CommentStatus status);

    @Modifying
    @Query(value = "UPDATE Comment c set c.replyCount = c.replyCount + 1 where  c=:comment")
    void increaseReplyCount(Comment comment);

    @Modifying
    @Query(value = "UPDATE Comment c set c.replyCount = c.replyCount - 1 where  c=:comment")
    void decreaseReplyCount(Comment comment);
}
