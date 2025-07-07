package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.stereotype.Service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserReadService {

    private final UserRepository userRepository;

    public UserProfileResponse getUserInfo(Long userId){

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return UserProfileResponse.of(userId, user.getName(), user.getNickname(), user.getProfileImage(), user.isRecommendInsurance());

    }
}
