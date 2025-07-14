package org.sopt.bofit.domain.comment.repository;

import java.util.List;

import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPostIdAndStatus(Long postId, CommentStatus status);

	long countByPost(Post post);
}
