package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;
    
    public UserProfileResponse getUserInfo(Long userId){

        User user = findById(userId);

        return UserProfileResponse.of(userId, user.getName(), user.getNickname(), user.getProfileImage(), user.isRecommendInsurance());

    }

    public SliceResponse<MyPostSummaryResponse, Long> getMyPosts(Long userId, Long cursorId, int size) {

        findById(userId);

        Slice<MyPostSummaryResponse> posts = postRepository.findPostsByCursorId(userId, cursorId, size);

        return SliceResponse.of(posts);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public SliceResponse<MyCommentSummaryResponse, Long> getMyComments(Long userId, Long cursorId, int size) {

        findById(userId);

        Slice<MyCommentSummaryResponse> comments = commentRepository.findCommentsByCursorId(userId, cursorId, size);

        return SliceResponse.of(comments);
    }

}
