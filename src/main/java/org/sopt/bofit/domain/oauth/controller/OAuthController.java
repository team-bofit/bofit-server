package org.sopt.bofit.domain.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.oauth.dto.KaKaoTokenResponse;
import org.sopt.bofit.domain.oauth.service.OAuthService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.config.swagger.SwaggerResponseDescription;
import org.sopt.bofit.global.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @CustomExceptionDescription(KAKAO_TOKEN_REQUEST)
    @Operation(summary = "카카오 토큰 요청", description = "카카오 로그인 후 인가 코드를 요청합니다.")
    @GetMapping("/kakao/callback")
    public Mono<BaseResponse<KaKaoTokenResponse>> kakaoCallback(@RequestParam("code") String code) {
        return oAuthService.requestToken(code)
                .map(token -> BaseResponse.ok(token, "카카오 로그인 성공"));
    }
}
