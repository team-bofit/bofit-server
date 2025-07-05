package org.sopt.bofit.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.domain.oauth.dto.KaKaoTokenResponse;
import org.sopt.bofit.domain.oauth.dto.KakaoUserResponse;
import org.sopt.bofit.domain.oauth.util.OAuthUtil;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.sopt.bofit.domain.oauth.dto.KakaoUserResponse.*;
import static org.sopt.bofit.domain.oauth.dto.KakaoUserResponse.KakaoAccount.*;
import static org.sopt.bofit.domain.oauth.util.UserInfoUtil.*;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;

    private final WebClient webClient = WebClient.create();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    public Mono<KaKaoTokenResponse> requestToken(String code) {
        String body = OAuthUtil.buildTokenRequestBody(code, clientId, redirectUri);

        return webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
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

    private Mono<KakaoUserResponse> getUserInfo(String accessToken) {
        return webClient.get()
                .uri(userInfoUri)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserResponse.class);
    }

    public Mono<User> registerOrLogin(String accessToken) {
        return getUserInfo(accessToken)
                .flatMap(kakaoUser -> {
                    KakaoAccount account = kakaoUser.getKakaoAccount();
                    if (account == null) {
                        log.error("❌kakao_account 정보가 없습니다. 사용자 동의 누락 가능성");
                        return Mono.error(new BadRequestException(KAKAO_USER_INFO_REQUEST_FAILED));
                    }
                    UserProfile profile = account.getProfile();

                    return Mono.fromCallable(() ->
                                    userRepository.findByOauthId(String.valueOf(kakaoUser.getOauthId()))
                            )
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(optionalUser ->
                                    optionalUser.map(Mono::just).orElseGet(() -> {
                                        User newUser = User.create(
                                                LoginProvider.KAKAO,
                                                String.valueOf(kakaoUser.getOauthId()),
                                                account.getName(),
                                                profile.getNickname(),
                                                profile.getProfile_image_url(),
                                                parseGender(account.getGender()),
                                                parseBirthYear(account.getBirthyear())
                                        );
                                        return Mono.fromCallable(() -> userRepository.save(newUser))
                                                .subscribeOn(Schedulers.boundedElastic());
                                    })
                            );
                });
    }
}



