package org.sopt.bofit.global.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.custom_exception.UnAuthorizedException;
import org.sopt.bofit.global.oauth.dto.KaKaoLoginResponse;
import org.sopt.bofit.global.oauth.dto.KaKaoTokenResponse;
import org.sopt.bofit.global.oauth.dto.KakaoUserResponse;
import org.sopt.bofit.global.oauth.dto.TokenReissueResponse;
import org.sopt.bofit.global.oauth.entity.RefreshToken;
import org.sopt.bofit.global.oauth.jwt.JwtProvider;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.sopt.bofit.global.oauth.repository.RefreshTokenRepository;
import org.sopt.bofit.global.oauth.util.OAuthUtil;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.JWT_INVALID;
import static org.sopt.bofit.global.oauth.dto.KakaoUserResponse.*;
import static org.sopt.bofit.global.oauth.dto.KakaoUserResponse.KakaoAccount.*;
import static org.sopt.bofit.global.oauth.util.UserAccountUtil.*;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final JwtUtil jwtUtil;

    private final WebClient webClient = WebClient.create();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    private Mono<KaKaoTokenResponse> requestToken(String code) {
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

    private Mono<User> registerOrLogin(String accessToken) {
        return getUserInfo(accessToken)
                .flatMap(kakaoUser -> {
                    KakaoAccount account = kakaoUser.kakaoAccount();
                    if (account == null) {
                        log.error("❌kakao_account 정보가 없습니다. 사용자 동의 누락 가능성");
                        return Mono.error(new BadRequestException(KAKAO_USER_INFO_REQUEST_FAILED));
                    }
                    UserProfile profile = account.profile();

                    return Mono.fromCallable(() ->
                                    userRepository.findByOauthId(String.valueOf(kakaoUser.oauthId()))
                            )
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(optionalUser ->
                                    optionalUser.map(Mono::just).orElseGet(() -> {
                                        User newUser = User.create(
                                                LoginProvider.KAKAO,
                                                String.valueOf(kakaoUser.oauthId()),
                                                account.name(),
                                                profile.nickname(),
                                                profile.profile_image_url(),
                                                parseGender(account.gender()),
                                                parseBirth(account.birthday()),
                                                parseBirth(account.birthyear())
                                        );
                                        return Mono.fromCallable(() -> userRepository.save(newUser))
                                                .subscribeOn(Schedulers.boundedElastic());
                                    })
                            );
                });
    }

    @Transactional
    public Mono<KaKaoLoginResponse> login(String code) {
        return requestToken(code)
                .flatMap(token ->
                        registerOrLogin(token.access_token())
                                .publishOn(Schedulers.boundedElastic())
                                .map(user -> {
                                    String accessToken = jwtProvider.generateAccessToken(user.getId());
                                    String refreshToken = jwtProvider.generateRefreshToken(user.getId());

                                    refreshTokenRepository.findByUserId(user.getId())
                                            .ifPresentOrElse(
                                                    existing -> existing.updateToken(refreshToken),
                                                    () -> refreshTokenRepository.save(RefreshToken.of(user.getId(), refreshToken))
                                            );
                                    return KaKaoLoginResponse.of(
                                            user.getId(),
                                            user.isRegistered(),
                                            accessToken,
                                            refreshToken
                                    );
                                })
                );
    }

    @Transactional
    public TokenReissueResponse reissue(String refreshToken) {
        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new UnAuthorizedException(JWT_INVALID);
        }

        Long userId = jwtUtil.extractUserIdFromToken(refreshToken);

        RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new UnAuthorizedException(JWT_REFRESH_NOT_FOUND));

        if (!savedToken.getRefreshToken().equals(refreshToken)) {
            throw new UnAuthorizedException(JWT_REFRESH_TOKEN_MISMATCH);
        }

        String newAccessToken = jwtProvider.generateAccessToken(userId);
        String newRefreshToken = jwtProvider.generateRefreshToken(userId);
        savedToken.updateToken(newRefreshToken);
        refreshTokenRepository.save(savedToken);

        return TokenReissueResponse.of(newAccessToken, newRefreshToken);
    }

}



