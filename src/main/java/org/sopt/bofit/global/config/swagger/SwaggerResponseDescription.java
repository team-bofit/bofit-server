package org.sopt.bofit.global.config.swagger;

import lombok.Getter;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.OAuthErrorCode;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;


@Getter
public enum SwaggerResponseDescription {
    KAKAO_TOKEN_REQUEST(new LinkedHashSet<>(Set.of(
            KAKAO_TOKEN_REQUEST_FAILED,
            KAKAO_USER_INFO_REQUEST_FAILED
    )))
    ;
    private final Set<ErrorCode> errorCodeList;
    SwaggerResponseDescription(Set<ErrorCode> errorCodeList) {
        this.errorCodeList = errorCodeList;
    }
}
