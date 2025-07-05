package org.sopt.bofit.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.oauth.dto.KaKaoLoginResponse;
import org.sopt.bofit.global.oauth.dto.TokenReissueResponse;
import org.sopt.bofit.global.oauth.service.OAuthService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.response.BaseResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @CustomExceptionDescription(KAKAO_TOKEN_REQUEST)
    @Operation(summary = "카카오 로그인", description = "카카오 API를 통해 로그인합니다.")
    @GetMapping("/kakao/login")
    public Mono<BaseResponse<KaKaoLoginResponse>> kakaoCallback(@RequestParam("code") String code) {
        return oAuthService.login(code)
                .map(response -> BaseResponse.ok(response, "카카오 로그인 성공"));
    }

    @PostMapping("/reissue")
    public BaseResponse<TokenReissueResponse> reissue(@RequestHeader("Authorization") String bearerToken) {
        String refreshToken = bearerToken.replace("Bearer ", "").trim();
        return BaseResponse.ok(oAuthService.reissue(refreshToken), "토큰 재발급 성공");
    }

}
