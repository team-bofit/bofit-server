package org.sopt.bofit.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.service.PostService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
}
