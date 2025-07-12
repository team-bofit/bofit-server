package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.repository.PostCustomRepositoryImpl;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.exception.custom_exception.ForbiddenException;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostReader postReader;

    private final PostWriter postWriter;

    public PostCreateResponse createPost(Long userId, String title, String content) {
        return postWriter.createPost(userId, title, content);
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, String title, String content) {
        return postWriter.updatePost(userId, postId, title, content);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        postWriter.deletePost(userId, postId);
    }


}
