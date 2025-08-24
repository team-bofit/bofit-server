package org.sopt.bofit.global.oauth.controller;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;
import static org.sopt.bofit.global.constant.SwaggerConstant.*;

import java.util.Optional;

import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.oauth.dto.request.OAuthLoginRequest;
import org.sopt.bofit.global.oauth.dto.response.KaKaoLoginResponse;
import org.sopt.bofit.global.oauth.dto.response.TokenReissueResponse;
import org.sopt.bofit.global.oauth.service.OAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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

}
