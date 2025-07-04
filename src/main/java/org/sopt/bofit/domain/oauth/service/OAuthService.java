package org.sopt.bofit.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.domain.oauth.dto.KaKaoTokenResponse;
import org.sopt.bofit.domain.oauth.util.OAuthUtil;
import org.sopt.bofit.global.exception.constant.OAuthErrorCode;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final WebClient webClient = WebClient.create();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    public Mono<KaKaoTokenResponse> requestToken(String code) {
        String body = OAuthUtil.buildTokenRequestBody(code, clientId, redirectUri);

        return webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()

                // ✅ HTTP 에러 응답 처리 (ex. 400, 401)
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("❌ Kakao 토큰 요청 실패: {}", errorBody);
                                    return Mono.error(new BadRequestException(KAKAO_TOKEN_REQUEST_FAILED));
                                })
                )

                .bodyToMono(KaKaoTokenResponse.class)
                .doOnNext(token -> log.info("✅ Kakao access token 발급 성공"))
                .doOnError(e -> log.error("❌ Kakao 토큰 요청 중 예외 발생", e));
    }

}
