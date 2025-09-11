package org.sopt.bofit.global.oauth.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.config.properties.KakaoProperties;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.oauth.dto.response.KaKaoTokenResponse;
import org.sopt.bofit.global.oauth.dto.response.KakaoUserResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.KAKAO_TOKEN_REQUEST_FAILED;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthClient {
    private final RestClient restClient = RestClient.builder().baseUrl("").build();

    private final KakaoProperties properties;

    public KaKaoTokenResponse requestToken(String code, Optional<String> redirectUrl) {
        String body = OAuthUtil.buildTokenRequestBody(code, properties.clientId(),
                redirectUrl.orElseGet(properties::redirectUri));

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

    public KakaoUserResponse getUserInfo(String accessToken) {
        return restClient.get()
                .uri(properties.userInfoUri())
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .body(KakaoUserResponse.class);
    }

}
