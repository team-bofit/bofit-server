package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostLike;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.exception.constant.PostErrorCode;
import org.sopt.bofit.global.exception.customexception.ConflictException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostReader postReader;
    private final PostWriter postWriter;

    private final UserReader userReader;

    private final PostLikeReader postLikeReader;
    private final PostLikeWriter postLikeWriter;

    private final PostCacheService postCacheService;

    @Transactional
    public void createPostLike(Long userId, Long postId){
        Post post = postReader.getActiveById(postId);
        User user = userReader.getActiveById(userId);

        boolean isExistLike = postLikeReader.isExistsByPostAndUser(post, user);

        if(isExistLike){
            throw new ConflictException(PostErrorCode.POST_LIKE_CREATE_CONFLICT);
        }

        PostLike postLike = postLikeWriter.create(post, user);
        postWriter.increaseLikeCount(post);
        postCacheService.increaseLikeCount(postId);
    }

    @Transactional
    public void deletePostLike(Long userId, Long postId){
        Post post = postReader.getActiveById(postId);
        User user = userReader.getActiveById(userId);

        PostLike postLike = postLikeReader.findByPostAndUser(post, user);

        postLikeWriter.delete(postLike);

        postWriter.decreaseLikeCount(post);
        postCacheService.decreaseLikeCount(postId);
    }
}
