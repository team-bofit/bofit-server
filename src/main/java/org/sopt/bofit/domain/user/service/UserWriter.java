package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserWriter {

    private final UserRepository userRepository;

	private final UserReader userReader;

    @Transactional
    public User updateUser(
        User user,
        String name,
        Gender gender,
        LocalDate birthDate,
        Job job,
        boolean isMarried,
        boolean isDriver,
        boolean hasChild
    ){
    	user.updateName(name);
    	user.updateJob(job);
    	user.updateHasChild(hasChild);
    	user.updateDriver(isDriver);
    	user.updateGender(gender);
    	user.updateMarried(isMarried);
    	user.updateBirthDate(birthDate);

    	return user;
    }

	@Transactional
	public void updateUserNickname(Long userId, String newNickname) {
		User user = userReader.findById(userId);

		user.updateNickname(newNickname);
	}

}
