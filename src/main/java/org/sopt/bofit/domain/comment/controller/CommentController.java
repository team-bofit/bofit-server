package org.sopt.bofit.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
}
