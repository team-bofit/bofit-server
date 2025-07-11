package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.comment.repository.CommentCustomRepositoryImpl;
import org.sopt.bofit.domain.post.repository.PostCustomRepositoryImpl;
import org.sopt.bofit.domain.user.dto.response.CommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    private final PostCustomRepositoryImpl postCustomRepositoryImpl;

    private final CommentCustomRepositoryImpl commentCustomRepositoryImpl;
    
    public UserProfileResponse getUserInfo(Long userId){

        User user = findById(userId);

        return UserProfileResponse.of(userId, user.getNickname(), user.getProfileImage(), user.isRecommendInsurance());

    }

    public SliceResponse<PostSummaryResponse> getMyPosts(Long userId, Long cursorId, int size) {

        findById(userId);

        Slice<PostSummaryResponse> posts = postCustomRepositoryImpl.findPostsByCursorId(userId, cursorId, size);

        return SliceResponse.of(posts);
    }


    public JobResponses getJobs() {
        return JobResponses.create(Job.values());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public SliceResponse<CommentSummaryResponse> getMyComments(Long userId, Long cursorId, int size) {

        findById(userId);

        Slice<CommentSummaryResponse> comments = commentCustomRepositoryImpl.findCommentsByCursorId(userId, cursorId, size);

        return SliceResponse.of(comments);
    }

    public DiagnosedDiseaseResponses getDiagnosedDiseaseNames(){
        return DiagnosedDiseaseResponses.from(DiagnosedDisease.values());
    }
}
