package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.user.dto.response.CoveragePreferenceResponses;
import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserReader userReader;
	private final UserWriter userWriter;

	public UserProfileResponse getUserInfo(Long userId){
		return userReader.getUserInfo(userId);
	}

	public JobResponses getJobs(){
		return JobResponses.create(Job.values());
	}

	public DiagnosedDiseaseResponses getDiagnosedDiseaseNames(){
		return DiagnosedDiseaseResponses.from(DiagnosedDisease.values());

	}

	public CoveragePreferenceResponses  getCoveragePreference(){
		return CoveragePreferenceResponses.from(CoveragePreference.values());
	}

	@Transactional
	public User userUpdate(Long userId, PersonalInfo personalInfo){
		User user = userReader.findById(userId);

		return userWriter.updateUser(
			user,
            personalInfo
		);
	}

	public SliceResponse<MyPostSummaryResponse, Long> getMyPosts(Long userId, Long cursorId, int size){
		return userReader.getMyPosts(userId, cursorId, size);
	}

	public SliceResponse<MyCommentSummaryResponse, Long> getMyComments(Long userId, Long cursorId, int size) {
		return userReader.getMyComments(userId, cursorId, size);
	}

	public void updateProfile(Long userId, String nickname, String profileImageUrl) {
		userWriter.updateUserProfile(userId, nickname, profileImageUrl);
	}
}
