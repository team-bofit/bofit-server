package org.sopt.bofit.domain.user.service;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserWriter {

    private final UserRepository userRepository;

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

}
