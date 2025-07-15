package org.sopt.bofit.domain.user.service;

import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoWriter {
	private final UserInfoRepository userInfoRepository;

	public UserInfo save(UserInfo userInfo){
		return userInfoRepository.save(userInfo);
	}

}
