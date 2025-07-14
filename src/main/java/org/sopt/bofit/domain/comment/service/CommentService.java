package org.sopt.bofit.domain.comment.service;

import static org.sopt.bofit.global.exception.constant.CommentErrorCode.*;

import org.sopt.bofit.domain.comment.dto.request.CommentCreateRequest;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.PostReader;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.exception.constant.CommentErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentReader commentReader;
	private final CommentWriter commentWriter;

	private final PostReader postReader;

	private final UserReader userReader;

	@Transactional
	public void createComment(Long userId, Long postId, CommentCreateRequest request){
		Post post = postReader.findById(postId);
		User user = userReader.findById(userId);

		Comment comment = commentWriter.create(post, user, request.content());
	}

	@Transactional
	public void deleteComment(Long userId, Long postId, Long commentId) {
		Comment comment = commentReader.findById(commentId);

		comment.getUser().checkIsWriter(userId, COMMENT_UNAUTHORIZED);
		comment.checkPost(postId);

		commentWriter.softDelete(comment);
	}

}
