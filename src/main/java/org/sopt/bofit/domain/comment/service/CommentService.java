package org.sopt.bofit.domain.comment.service;

import static org.sopt.bofit.global.exception.constant.CommentErrorCode.COMMENT_UNAUTHORIZED;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.service.dto.request.CommentCreateCommand;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.PostReader;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentReader commentReader;
	private final CommentWriter commentWriter;

	private final PostReader postReader;

	private final UserReader userReader;

	private final CommentImageWriter commentImageWriter;

	@Transactional
	public Comment createComment(Long userId, Long postId, CommentCreateCommand command){
		Post post = postReader.getActiveById(postId);
		User user = userReader.getActiveById(userId);

		Comment comment = commentWriter.create(post, user, command.content());
		command.imageUrls()
			.forEach(url -> commentImageWriter.create(comment, url));

		return comment;
	}

	@Transactional
	public void deleteComment(Long userId, Long postId, Long commentId) {
		Comment comment = commentReader.getActiveById(commentId);
		Post post = postReader.getActiveById(postId);

		comment.getUser().checkIsWriter(userId, COMMENT_UNAUTHORIZED);
		comment.checkPost(post);

		commentWriter.softDelete(comment);
	}

	public SliceResponse<CommentResponse, Long> findAllByPostIdAndCursor(Long postId, Long userId, Optional<Long> cursor, int size) {
		Post post = postReader.getActiveById(postId);

		Slice<CommentResponse> commentsByCursorId = commentReader.findCommentsByCursorId(postId, cursor, size);

		return SliceResponse.from(commentsByCursorId);
	}
}
