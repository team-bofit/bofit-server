package org.sopt.bofit.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.sopt.bofit.global.dto.response.SliceResponse;
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

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;
    
    public UserProfileResponse getUserInfo(Long userId){

        User user = findById(userId);

        return UserProfileResponse.of(userId, user.getNickname(), user.getProfileImage(), user.isRecommendInsurance());

    }

    public SliceResponse<MyPostSummaryResponse> getMyPosts(Long userId, Long cursorId, int size) {

        findById(userId);

        Slice<MyPostSummaryResponse> posts = postRepository.findPostsByCursorId(userId, cursorId, size);

        return SliceResponse.of(posts);
    }


    public JobResponses getJobs() {
        return JobResponses.create(Job.values());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public SliceResponse<MyCommentSummaryResponse> getMyComments(Long userId, Long cursorId, int size) {

        findById(userId);

        Slice<MyCommentSummaryResponse> comments = commentRepository.findCommentsByCursorId(userId, cursorId, size);

        return SliceResponse.of(comments);
    }

    public DiagnosedDiseaseResponses getDiagnosedDiseaseNames(){
        return DiagnosedDiseaseResponses.from(DiagnosedDisease.values());
    }
}
