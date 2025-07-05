package org.sopt.bofit.global.oauth.util;


public class OAuthUtil {

    public static String buildTokenRequestBody(String code, String clientId, String redirectUri) {
        return "grant_type=authorization_code"
               + "&client_id=" + clientId
               + "&redirect_uri=" + redirectUri
               + "&code=" + code;
    }
}
