package org.sopt.bofit.domain.comment.service;

import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentWriter {
	private final CommentRepository commentRepository;

	@Transactional
	public Comment createComment(Post post, User user, String content){
		Comment comment = Comment.create(post, user, content);

		return commentRepository.save(comment);
	}
}
