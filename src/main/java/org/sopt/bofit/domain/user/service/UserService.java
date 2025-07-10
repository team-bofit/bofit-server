package org.sopt.bofit.domain.user.service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;

import org.sopt.bofit.domain.user.dto.response.CommentSummaryResponse;

import org.sopt.bofit.domain.user.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;

import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.dto.UserUpdate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserReader userReader;
	private final UserWriter userWriter;

	public UserProfileResponse getUserInfo(Long userId){
		return userReader.getUserInfo(userId);
	}

	public JobResponses getJobs(){
		return userReader.getJobs();
	}

	public DiagnosedDiseaseResponses getDiagnosedDiseaseNames(){
		return userReader.getDiagnosedDiseaseNames();
	}

	@Transactional
	public User userUpdate(Long userId, UserUpdate userUpdate){
		User user = userReader.findById(userId);

		return userWriter.updateUser(
			user,
			userUpdate.name(),
			userUpdate.gender(),
			userUpdate.birthDate(),
			userUpdate.job(),
			userUpdate.isMarried(),
			userUpdate.isDriver(),
			userUpdate.hasChild()
		);
	}

	public SliceResponse<PostSummaryResponse> getMyPosts(Long userId, Long cursorId, int size){
		return userReader.getMyPosts(userId, cursorId, size);
	}

	public SliceResponse<CommentSummaryResponse> getMyComments(Long userId, Long cursorId, int size) {
		return userReader.getMyComments(userId, cursorId, size);
	}
}
