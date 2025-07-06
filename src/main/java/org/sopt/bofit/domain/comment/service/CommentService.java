package org.sopt.bofit.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
}
