package org.sopt.bofit.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping("profile")
    public BaseResponse<UserProfileResponse> getProfile(
            @Parameter(hidden = true) @LoginUserId Long userId
         ) {
        return BaseResponse.ok(userService.getUserProfile(userId), "유저 프로필 조회 성공");
    }

}
