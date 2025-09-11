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
import org.sopt.bofit.global.oauth.dto.request.OAuthLoginRequest;
import org.sopt.bofit.global.oauth.dto.response.KaKaoLoginResponse;
import org.sopt.bofit.global.oauth.dto.response.KaKaoTokenResponse;
import org.sopt.bofit.global.oauth.dto.response.KakaoUserResponse;
import org.sopt.bofit.global.oauth.dto.response.TokenReissueResponse;
import org.sopt.bofit.global.oauth.entity.RefreshToken;
import org.sopt.bofit.global.oauth.jwt.JwtProvider;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.sopt.bofit.global.oauth.repository.RefreshTokenRepository;
import org.sopt.bofit.global.oauth.util.OAuthClient;
import org.sopt.bofit.global.oauth.util.OAuthUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.JWT_INVALID;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;
import static org.sopt.bofit.global.oauth.dto.response.KakaoUserResponse.KakaoAccount;
import static org.sopt.bofit.global.oauth.dto.response.KakaoUserResponse.KakaoAccount.UserProfile;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final JwtUtil jwtUtil;

    private final KakaoProperties properties;

    private final OAuthClient oAuthClient;

    private User registerOrLogin(String accessToken) {
        KakaoUserResponse kakaoUser = oAuthClient.getUserInfo(accessToken);
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
    public KaKaoLoginResponse login(String code, Optional<String> redirectUrl) {
        KaKaoTokenResponse token = oAuthClient.requestToken(code, redirectUrl);
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
    public KaKaoLoginResponse login(OAuthLoginRequest request) {
        KaKaoTokenResponse token = oAuthClient.requestToken(request.code(), Optional.ofNullable(request.redirectUrl()));
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

    @Transactional
    public String logout(Long userId, String redirectUri) {
        refreshTokenRepository.findByUserId(userId)
                .ifPresent(refreshTokenRepository::delete);

        return OAuthUtil.buildKakaoLogoutRedirectUrl(properties.logoutUri(), properties.clientId(), redirectUri).toString();
    }
}



