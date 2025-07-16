package org.sopt.bofit.global.oauth.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtExceptionConstants {
    public static final String EXPIRED = "JWT_EXPIRED";
    public static final String INVALID_SIGNATURE = "JWT_INVALID_SIGNATURE";
    public static final String INVALID = "JWT_INVALID";
    public static final String UNSUPPORTED = "JWT_UNSUPPORTED";
    public static final String NOT_EXIST = "JWT_NOT_EXIST";
}
