package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.request.PostRequest;
import org.sopt.bofit.domain.post.dto.response.PostResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.constant.UserErrorCode;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.stereotype.Service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostResponse createPost(Long userId, String title, String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Post newPost = Post.create(title, content);
        newPost.setUser(user);

        postRepository.save(newPost);
        return PostResponse.of(newPost.getId());
    }

}
