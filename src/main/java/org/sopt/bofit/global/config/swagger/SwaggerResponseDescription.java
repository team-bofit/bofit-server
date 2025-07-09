package org.sopt.bofit.global.config.swagger;

import lombok.Getter;
import org.sopt.bofit.global.exception.constant.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.*;
import static org.sopt.bofit.global.exception.constant.UserErrorCode.*;


@Getter
public enum SwaggerResponseDescription {
    KAKAO_TOKEN_REQUEST(new LinkedHashSet<>(Set.of(
            KAKAO_TOKEN_REQUEST_FAILED,
            KAKAO_USER_INFO_REQUEST_FAILED
    ))),
    TOKEN_REISSUE(new LinkedHashSet<>(Set.of(
            JWT_REFRESH_TOKEN_MISMATCH,
            JWT_REFRESH_NOT_FOUND
    ))),
    USER_INFO(new LinkedHashSet<>(Set.of(
            USER_NOT_FOUND
    ))),
    MY_POSTS(new LinkedHashSet<>(Set.of(
            USER_NOT_FOUND
    ))),
    MY_COMMENTS(new LinkedHashSet<>(Set.of(
            USER_NOT_FOUND
    ))),
    CREATE_POST(new LinkedHashSet<>(Set.of(
            USER_NOT_FOUND,
            POST_CONTENT_BLANK,
            POST_TITLE_BLANK,
            POST_CONTENT_LONG,
            POST_TITLE_LONG
    ))),
    UPDATE_POST(new LinkedHashSet<>(Set.of(
            USER_NOT_FOUND,
            POST_NOT_FOUND,
            POST_UNAUTHORIZED,
            POST_CONTENT_BLANK,
            POST_TITLE_BLANK,
            POST_CONTENT_LONG,
            POST_TITLE_LONG
    )))
    ;
    private final Set<ErrorCode> errorCodeList;
    SwaggerResponseDescription(Set<ErrorCode> specificErrorCodes) {
        this.errorCodeList = new LinkedHashSet<>();
        this.errorCodeList.addAll(specificErrorCodes);
        this.errorCodeList.addAll(getGlobalErrorCodes());
    }

    private Set<ErrorCode> getGlobalErrorCodes() {
        return new LinkedHashSet<>(Set.of(GlobalErrorCode.values()));
    }
}
