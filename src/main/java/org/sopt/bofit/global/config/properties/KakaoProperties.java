package org.sopt.bofit.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "kakao")
public record KakaoProperties(
        String clientId,
        String redirectUri,
        String tokenUri,
        String userInfoUri
) { }
