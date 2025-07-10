package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostResponse;
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

    private final PostRepository postRepository;

    private final UserReader userReader;

    private final PostCustomRepositoryImpl postCustomRepositoryImpl;

    public PostResponse createPost(Long userId, String title, String content) {
        User user = userReader.findById(userId);
        Post newPost = Post.create(title, content);
        newPost.setUser(user);

        postRepository.save(newPost);
        return PostResponse.from(newPost.getId());
    }

    @Transactional
    public PostResponse updatePost (Long userId, Long postId, String title, String content) {
        User user = userReader.findById(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        if(!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException(POST_UNAUTHORIZED);
        }

        post.updatePost(title, content);
        return PostResponse.from(post.getId());
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        User user = userReader.findById(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        if(!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException(POST_UNAUTHORIZED);
        }

        postCustomRepositoryImpl.deletePostByPostId(postId);
    }


}
