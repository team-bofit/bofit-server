package org.sopt.bofit.domain.comment.service;

import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.global.exception.constant.CommentErrorCode;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentReader {
	private final CommentRepository commentRepository;

	public Comment findById(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(() ->
			new NotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
	}
}
