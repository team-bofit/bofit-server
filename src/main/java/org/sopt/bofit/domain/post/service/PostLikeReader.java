package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostLike;
import org.sopt.bofit.domain.post.repository.PostLikeRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.exception.constant.PostErrorCode;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeReader {

    private final PostLikeRepository postLikeRepository;

    public boolean isExistsByPostAndUser(Post post, User user){
        return postLikeRepository.existsByPostAndUser(post, user);
    }

    public PostLike findByPostAnsUser(Post post, User user){
        return postLikeRepository.findByPostAndUser(post, user)
            .orElseThrow(() -> new NotFoundException(PostErrorCode.POST_LIKE_DELETE_CONFLICT));
    }
}
