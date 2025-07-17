package org.sopt.bofit.global.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.config.properties.KakaoProperties;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.exception.customexception.UnAuthorizedException;
import org.sopt.bofit.global.oauth.constant.HttpHeaderConstants;
import org.sopt.bofit.global.oauth.dto.KaKaoLoginResponse;
import org.sopt.bofit.global.oauth.dto.KaKaoTokenResponse;
import org.sopt.bofit.global.oauth.dto.KakaoUserResponse;
import org.sopt.bofit.global.oauth.dto.TokenReissueResponse;
import org.sopt.bofit.global.oauth.entity.RefreshToken;
import org.sopt.bofit.global.oauth.jwt.JwtProvider;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.sopt.bofit.global.oauth.repository.RefreshTokenRepository;
import org.sopt.bofit.global.oauth.util.OAuthUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.JWT_INVALID;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;
import static org.sopt.bofit.global.oauth.dto.KakaoUserResponse.KakaoAccount;
import static org.sopt.bofit.global.oauth.dto.KakaoUserResponse.KakaoAccount.UserProfile;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final JwtUtil jwtUtil;

    private final KakaoProperties properties;

    private final RestClient restClient = RestClient.builder().baseUrl("").build();

    private KaKaoTokenResponse requestToken(String code) {
        String body = OAuthUtil.buildTokenRequestBody(code, properties.clientId(), properties.redirectUri());

        return restClient.post()
                .uri(properties.tokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        (req, res) -> {
                            String errorBody = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
                            log.error("❌ Kakao 토큰 요청 실패: {}", errorBody);
                            throw new BadRequestException(KAKAO_TOKEN_REQUEST_FAILED);
                        })
                .body(KaKaoTokenResponse.class);
    }

    private KakaoUserResponse getUserInfo(String accessToken) {
        return restClient.get()
                .uri(properties.userInfoUri())
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .body(KakaoUserResponse.class);
    }

    private User registerOrLogin(String accessToken) {
        KakaoUserResponse kakaoUser = getUserInfo(accessToken);
        KakaoAccount account = kakaoUser.kakaoAccount();
        if (account == null) {
            throw new BadRequestException(KAKAO_USER_INFO_REQUEST_FAILED);
        }

        UserProfile profile = account.profile();
        boolean isDefault = profile.isDefaultImage();
        String userProfileImage = isDefault ? null : profile.profileImageUrl();

        return userRepository.findByOauthId(String.valueOf(kakaoUser.oauthId()))
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .loginProvider(LoginProvider.KAKAO)
                            .oauthId(String.valueOf(kakaoUser.oauthId()))
                            .nickname(profile.nickname())
                            .profileImage(userProfileImage)
                            .build();
                    return userRepository.save(newUser);
                });
    }

    @Transactional
    public KaKaoLoginResponse login(String code) {
        KaKaoTokenResponse token = requestToken(code);
        User user = registerOrLogin(token.accessToken());

        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        existing -> existing.updateToken(refreshToken),
                        () -> refreshTokenRepository.save(RefreshToken.of(user.getId(), refreshToken))
                );
        return KaKaoLoginResponse.of(user.getId(), accessToken, refreshToken);
    }

    @Transactional
    public TokenReissueResponse reissue(String bearerToken) {
        String refreshToken = bearerToken.replace(HttpHeaderConstants.BEARER_PREFIX, "").trim();
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



