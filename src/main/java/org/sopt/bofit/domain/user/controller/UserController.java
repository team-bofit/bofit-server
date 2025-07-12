package org.sopt.bofit.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Tag(name = "My Page", description = "마이페이지 관련 API")
    @CustomExceptionDescription(USER_INFO)
    @Operation(summary = "유저 정보 조회", description = "마이 페이지에서 유저의 정보를 조회합니다.")
    @GetMapping("info")
    public BaseResponse<UserProfileResponse> getInfo(
            @Parameter(hidden = true) @LoginUserId Long userId
         ) {
        return BaseResponse.ok(userService.getUserInfo(userId), "유저 프로필 조회 성공");
    }

    @Tag(name = "Users", description = "유저 관련 API")
    @Operation(summary = "직업 목록 조회", description = "선택 가능한 직업 목록을 조회합니다.")
    @GetMapping("/jobs")
    public BaseResponse<JobResponses> getJobs() {
        return BaseResponse.ok(userService.getJobs(), "선택 가능한 직업 목록 조회 성공");
    }

    @Tag(name = "My Page", description = "마이페이지 관련 API")
    @CustomExceptionDescription(MY_POSTS)
    @Operation(summary = "내가 쓴 글 조회", description = "마이페이지에서 내가 쓴 글을 조회합니다.")
    @GetMapping("me/posts")
    public BaseResponse<SliceResponse<MyPostSummaryResponse>> getMyPosts(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") int size
    ){
        return BaseResponse.ok(userService.getMyPosts(userId, cursorId, size), "내가 쓴 글 조회 성공");
    }

    @Tag(name = "My Page", description = "마이페이지 관련 API")
    @CustomExceptionDescription(MY_COMMENTS)
    @Operation(summary = "내가 쓴 댓글 조회", description = "마이페이지에서 내가 쓴 댓글을 조회합니다.")
    @GetMapping("me/comments")
    public BaseResponse<SliceResponse<MyCommentSummaryResponse>> getMyComments(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") int size
    ){
        return BaseResponse.ok(userService.getMyComments(userId, cursorId, size), "내가 쓴 댓글 조회 성공");
    }

    @Tag(name = "UserInfo", description = "유저 정보 관련 API")
    @Operation(summary = "진단 받은 질병 목록 조회", description = "선택 가능한 진단 받았던 질병 목록을 조회합니다.")
    @GetMapping("/diagnosed-disease")
    public BaseResponse<DiagnosedDiseaseResponses> getDiagnosedDisease() {
        return BaseResponse.ok(userService.getDiagnosedDiseaseNames(), "진단 받은 질병 목록 조회 성공");
    }


}
