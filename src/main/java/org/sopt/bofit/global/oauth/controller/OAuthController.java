package org.sopt.bofit.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.oauth.dto.request.OAuthLoginRequest;
import org.sopt.bofit.global.oauth.dto.response.KaKaoLoginResponse;
import org.sopt.bofit.global.oauth.dto.response.TokenReissueResponse;
import org.sopt.bofit.global.oauth.service.OAuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.KAKAO_TOKEN_REQUEST;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.TOKEN_REISSUE;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_DESCRIPTION_KAKAO_LOGIN;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_NAME_KAKAO_LOGIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @Tag(name = TAG_NAME_KAKAO_LOGIN, description = TAG_DESCRIPTION_KAKAO_LOGIN)
    @CustomExceptionDescription(KAKAO_TOKEN_REQUEST)
    @Operation(summary = "카카오 로그인", description = "카카오 API를 통해 로그인합니다.")
    @GetMapping("/kakao/login")
    public BaseResponse<KaKaoLoginResponse> kakaoCallback(
        @RequestParam("code") String code,
        @RequestParam(value = "redirect-url", required = false) String redirectUrl
    ) {
        return BaseResponse.ok(oAuthService.login(code, Optional.ofNullable(redirectUrl)), "카카오 로그인 성공");
    }

    @Tag(name = TAG_NAME_KAKAO_LOGIN, description = TAG_DESCRIPTION_KAKAO_LOGIN)
    @CustomExceptionDescription(KAKAO_TOKEN_REQUEST)
    @Operation(summary = "카카오 로그인 - POST", description = "카카오 API를 통해 로그인합니다. POST 요청을 사용하여 Body에 필요한 데이터를 받아옵니다.")
    @PostMapping("/kakao/login")
    public BaseResponse<KaKaoLoginResponse> kakaoCallback(@RequestBody OAuthLoginRequest oAuthLoginRequest) {
        return BaseResponse.ok(oAuthService.login(oAuthLoginRequest), "카카오 로그인 성공");
    }

    @Tag(name = TAG_NAME_KAKAO_LOGIN, description = TAG_DESCRIPTION_KAKAO_LOGIN)
    @CustomExceptionDescription(TOKEN_REISSUE)
    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public BaseResponse<TokenReissueResponse> reissue(@Parameter(hidden = true) @RequestHeader("Authorization") String refreshToken) {
        return BaseResponse.ok(oAuthService.reissue(refreshToken), "토큰 재발급 성공");
    }
    @Tag(name = TAG_NAME_KAKAO_LOGIN, description = TAG_DESCRIPTION_KAKAO_LOGIN)
    @Operation(summary = "로그아웃")
    @PostMapping("/kakao/logout")
    public BaseResponse<String> logout(
           @LoginUserId @Parameter(hidden = true) Long userId,
           @RequestParam(value = "redirect-url", required = false) String redirectUrl){
        return BaseResponse.ok(oAuthService.logout(userId, redirectUrl), "카카오 로그아웃 성공, 반환된 URL로 리다이렉트 시켜주세요");
    }


}
