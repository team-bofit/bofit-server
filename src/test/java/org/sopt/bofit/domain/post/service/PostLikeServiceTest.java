package org.sopt.bofit.domain.post.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostLike;
import org.sopt.bofit.domain.post.repository.PostLikeRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.PostErrorCode;
import org.sopt.bofit.global.exception.customexception.ConflictException;
import org.sopt.bofit.global.exception.customexception.CustomException;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostLikeServiceTest extends IntegrationTestSupport {

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @AfterEach
    void clearInAfterTest(){
        postLikeRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    private <T extends CustomException> void assertExceptionAndErrorCode(
        Runnable action,
        ErrorCode errorCode,
        Class<T> customException)
    {
        assertThatThrownBy(action::run)
            .isInstanceOf(customException)
            .satisfies(e -> assertThat(((T)e).getErrorCode()).isEqualTo(errorCode));
    }


    @DisplayName("게시글 좋아요가 정상적으로 생성됨")
    @Test
    void createPostLike(){
        // given
        User user = User.builder()
            .name("유저1")
            .loginProvider(LoginProvider.KAKAO)
            .oauthId("0123456")
            .build();
        userRepository.save(user);

        Post post = Post.create(user, "testTitle", "testComment", "QNA");
        postRepository.save(post);

        assertThat(post.getLikeCount()).isEqualTo(0);

        // when
        postLikeService.createPostLike(user.getId(), post.getId());

        // then
        Post updatedPost = postRepository.findById(post.getId()).get();
        assertThat(updatedPost.getLikeCount()).isEqualTo(1);
        assertTrue(postLikeRepository.existsByPostAndUser(post, user));
    }

    @DisplayName("게시글 좋아요가 이미 존재하는 경우 예외가 발생함")
    @Test
    void createPostLikeConflict(){
        // given
        User user = User.builder()
            .name("유저1")
            .loginProvider(LoginProvider.KAKAO)
            .oauthId("0123456")
            .build();
        userRepository.save(user);

        Post post = Post.create(user, "testTitle", "testComment", "QNA");
        postRepository.save(post);

        PostLike postLike = PostLike.create(post, user);
        postLikeRepository.save(postLike);

        // when // then
        assertExceptionAndErrorCode(
            ()-> postLikeService.createPostLike(user.getId(), post.getId()),
            PostErrorCode.POST_LIKE_CREATE_CONFLICT,
            ConflictException.class);
    }

    @DisplayName("게시글 좋아요가 정상적으로 삭제됨")
    @Test
    void deletePostLike(){
        // given
        User user = User.builder()
            .name("유저1")
            .loginProvider(LoginProvider.KAKAO)
            .oauthId("0123456")
            .build();
        userRepository.save(user);

        Post post = Post.create(user, "testTitle", "testComment","QNA");
        postRepository.save(post);

        postLikeService.createPostLike(user.getId(), post.getId());

        Post updatedPost = postRepository.findById(post.getId()).get();

        assertThat(updatedPost.getLikeCount()).isEqualTo(1);

        // when
        postLikeService.deletePostLike(user.getId(), post.getId());

        // then
        updatedPost = postRepository.findById(post.getId()).get();
        assertThat(updatedPost.getLikeCount()).isEqualTo(0);
        assertFalse(postLikeRepository.existsByPostAndUser(post, user));
    }

    @DisplayName("게시글에 좋아요가 존재하지 않는 상태에서 삭제 요청을 보내는 경우 예외가 발생함")
    @Test
    void deletePostLikeConflict(){
        // given
        User user = User.builder()
            .name("유저1")
            .loginProvider(LoginProvider.KAKAO)
            .oauthId("0123456")
            .build();
        userRepository.save(user);

        Post post = Post.create(user, "testTitle", "testComment","QNA");
        postRepository.save(post);

        // when // then
        assertExceptionAndErrorCode(
            () -> postLikeService.deletePostLike(user.getId(), post.getId()),
            PostErrorCode.POST_LIKE_NOT_FOUND,
            NotFoundException.class);
    }

}