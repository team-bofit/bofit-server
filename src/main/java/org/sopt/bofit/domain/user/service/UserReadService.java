package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.repository.PostCustomRepositoryImpl;
import org.sopt.bofit.domain.user.dto.response.MyPostsResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserReadService {

    private final UserRepository userRepository;

    private final PostCustomRepositoryImpl postCustomRepositoryImpl;

    public UserProfileResponse getUserInfo(Long userId){

        User user = findUserById(userId);

        return UserProfileResponse.of(userId, user.getName(), user.getNickname(), user.getProfileImage(), user.isRecommendInsurance());

    }

    public SliceResponse<MyPostsResponse> getMyPosts(Long userId, Long cursorId, int size) {

        findUserById(userId);

        Slice<MyPostsResponse> posts = postCustomRepositoryImpl.findMyPostsByCursor(userId, cursorId, size);

        return SliceResponse.of(posts);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
