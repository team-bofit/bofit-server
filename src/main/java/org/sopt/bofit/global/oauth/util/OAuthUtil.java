package org.sopt.bofit.global.oauth.util;


import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class OAuthUtil {

    public static String buildTokenRequestBody(String code, String clientId, String redirectUri) {
        return "grant_type=authorization_code"
               + "&client_id=" + clientId
               + "&redirect_uri=" + redirectUri
               + "&code=" + code;
    }

    public static URI buildKakaoLogoutRedirectUrl(String logoutBaseUri, String clientId, String logoutRedirectUri) {
        return UriComponentsBuilder
                .fromHttpUrl(logoutBaseUri)
                .queryParam("client_id", clientId)
                .queryParam("logout_redirect_uri", logoutRedirectUri)
                .build(true)
                .toUri();
    }
}
