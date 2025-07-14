package org.sopt.bofit.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurancereport.dto.response.InsuranceReportSummaryResponse;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;
import static org.sopt.bofit.global.constant.SwaggerConstant.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final InsuranceReportService insuranceReportService;

    @Tag(name = TAG_NAME_USER_INFO, description = TAG_DESCRIPTION_USER_INFO)
    @CustomExceptionDescription(USER_INFO)
    @Operation(summary = "유저 정보 조회", description = "마이 페이지에서 유저의 정보를 조회합니다.")
    @GetMapping("info")
    public BaseResponse<UserProfileResponse> getInfo(
            @Parameter(hidden = true) @LoginUserId Long userId
         ) {
        return BaseResponse.ok(userService.getUserInfo(userId), "유저 프로필 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
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

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
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

    @Tag(name = TAG_NAME_INSURANCE, description = TAG_DESCRIPTION_INSURANCE)
    @CustomExceptionDescription(GET_MY_LAST_INSURANCE_REPORT_SUMMARY)
    @Operation(summary = "최근 추천 리포트 요약 조회", description = "가장 최근에 추천 받은 보험 리포트의 요약 내용을 조회합니다.")
    @GetMapping("/me/report-summary")
    public BaseResponse<InsuranceReportSummaryResponse> getMyLastInsuranceReportSummary(
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        InsuranceReportSummaryResponse response
            = insuranceReportService.findUsersLastReportSummary(userId);
        return BaseResponse.ok(response, "최근 추천 리포트 요약 조회");
    }

}
