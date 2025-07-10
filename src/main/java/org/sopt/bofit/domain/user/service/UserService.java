package org.sopt.bofit.domain.user.service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;

import org.sopt.bofit.domain.user.dto.response.CommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.JobNameResponse;
import org.sopt.bofit.domain.user.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserReader userReader;
	private final UserWriter userWriter;

	public UserProfileResponse getUserInfo(Long userId){
		return userReader.getUserInfo(userId);
	}

	public JobNameResponse getJobs(){
		return userReader.getJobs();
	}

	public SliceResponse<PostSummaryResponse> getMyPosts(Long userId, Long cursorId, int size){
		return userReader.getMyPosts(userId, cursorId, size);
	}

	public SliceResponse<CommentSummaryResponse> getMyComments(Long userId, Long cursorId, int size) {
		return userReader.getMyComments(userId, cursorId, size);
	}
}
