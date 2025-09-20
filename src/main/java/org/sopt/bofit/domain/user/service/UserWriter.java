package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.domain.user.service.dto.request.UserInfoUpdateCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserWriter {

    private final UserRepository userRepository;

	private final PostRepository postRepository;

	private final UserReader userReader;

    @Transactional
    public User updateUser(
        User user,
        UserInfoUpdateCommand userInfoUpdateCommand
    ){
        user.updateName(userInfoUpdateCommand.name());
        user.updateJob(userInfoUpdateCommand.job());
        user.updateHasChild(userInfoUpdateCommand.hasChild());
        user.updateDriver(userInfoUpdateCommand.isDriver());
        user.updateGender(userInfoUpdateCommand.gender());
        user.updateMarried(userInfoUpdateCommand.isMarried());
        user.updateBirthDate(userInfoUpdateCommand.birthDate());

        user.recommendedInsurance();

        return user;
    }

	@Transactional
	public void updateUserNickname(Long userId, String newNickname) {
		User user = userReader.findById(userId);

		postRepository.updateWriterNicknameByUserId(newNickname, user.getId());

		user.updateNickname(newNickname);
	}

	@Transactional
	public void updateUserProfileImage(Long userId, String newProfileImageUrl) {
		User user = userReader.findById(userId);

		user.updateProfileImageUrl(newProfileImageUrl);
	}

	@Transactional
	public void unlinkUser(Long userId) {
		User user = userReader.findById(userId);
		user.deactivate();
		user.updateOauthId(user.getOauthId() + ":INACTIVE");

		postRepository.updateWriterNicknameByUserId(user.getId(), user.getNickname());
	}

}
