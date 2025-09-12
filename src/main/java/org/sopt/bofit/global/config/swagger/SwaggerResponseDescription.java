package org.sopt.bofit.global.config.swagger;

import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.INVALID_REPORT_SECTION;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT;
import static org.sopt.bofit.global.exception.constant.CommentErrorCode.COMMENT_ALREADY_DELETED;
import static org.sopt.bofit.global.exception.constant.CommentErrorCode.COMMENT_NOT_FOUND;
import static org.sopt.bofit.global.exception.constant.CommentErrorCode.UNMATCHED_COMMENT_IMAGE;
import static org.sopt.bofit.global.exception.constant.CommentErrorCode.UNMATCHED_COMMENT_POST;
import static org.sopt.bofit.global.exception.constant.InsuranceErrorCode.NOT_FOUND_INSURANCE_TOTAL_AVERAGE;
import static org.sopt.bofit.global.exception.constant.InsuranceErrorCode.NOT_FOUND_RECOMMENDED_STATUS_INSURANCE;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.JWT_REFRESH_NOT_FOUND;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.JWT_REFRESH_TOKEN_MISMATCH;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.KAKAO_TOKEN_REQUEST_FAILED;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.KAKAO_USER_INFO_REQUEST_FAILED;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_CONTENT_BLANK;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_CONTENT_LONG;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_IMAGE_MISMATCH;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_LIKE_CREATE_CONFLICT;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_LIKE_NOT_FOUND;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_NOT_FOUND;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_TITLE_BLANK;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_TITLE_LONG;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_UNAUTHORIZED;
import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_IMAGE_TYPE;
import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_MEDIA_TYPE;
import static org.sopt.bofit.global.exception.constant.UserErrorCode.USER_NOT_FOUND;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;


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
            POST_TITLE_LONG,
            POST_IMAGE_MISMATCH
    ))),
    DELETE_POST(new LinkedHashSet<>(Set.of(
            USER_NOT_FOUND,
            POST_NOT_FOUND,
            POST_UNAUTHORIZED
    ))),
    ISSUE_INSURANCE_REPORT(new LinkedHashSet<>(Set.of(
        NOT_FOUND_INSURANCE_TOTAL_AVERAGE,
        NOT_FOUND_RECOMMENDED_STATUS_INSURANCE
    ))),
    GET_INSURANCE_REPORT(new LinkedHashSet<>(Set.of(
        NOT_FOUND_INSURANCE_REPORT,
        INVALID_REPORT_SECTION
    ))),
    POST_DETAIL(new LinkedHashSet<>(Set.of(
            POST_NOT_FOUND
    ))),
    GET_MY_LAST_INSURANCE_REPORT_SUMMARY(new LinkedHashSet<>(Set.of(
        NOT_FOUND_INSURANCE_REPORT
    ))),
    CREATE_COMMENT(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND,
        POST_NOT_FOUND
    ))),
    UPDATE_COMMENT(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND,
        POST_NOT_FOUND,
        COMMENT_NOT_FOUND,
        UNMATCHED_COMMENT_POST,
        UNMATCHED_COMMENT_IMAGE
    ))),
    DELETE_COMMENT(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND,
        POST_NOT_FOUND,
        COMMENT_NOT_FOUND,
        COMMENT_ALREADY_DELETED
    ))),
    CREATE_POST_LIKE(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND,
        POST_NOT_FOUND,
        POST_LIKE_CREATE_CONFLICT
    ))),
    DELETE_POST_LIKE(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND,
        POST_NOT_FOUND,
        POST_LIKE_NOT_FOUND
    ))),
    UPLOAD_IMAGE(new LinkedHashSet<>(Set.of(
            UNSUPPORTED_MEDIA_TYPE,
            UNSUPPORTED_IMAGE_TYPE
    ))),
    CREATE_COMMENT_REPLY(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND,
        COMMENT_NOT_FOUND,
        POST_NOT_FOUND,
        UNMATCHED_COMMENT_POST
    ))),
    UPDATE_NICKNAME(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND
    ))),
    UPDATE_PROFILE_IMAGE(new LinkedHashSet<>(Set.of(
        USER_NOT_FOUND
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
