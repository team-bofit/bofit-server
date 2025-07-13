package org.sopt.bofit.domain.comment.service;

import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentReader {
	private final CommentRepository commentRepository;
}
