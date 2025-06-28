package org.sopt.bofit.global.config.swagger;

import org.sopt.bofit.global.exception.constant.ErrorCode;

import java.util.Set;


public enum SwaggerResponseDescription {
    ;
    private final Set<ErrorCode> errorCodeList;
    SwaggerResponseDescription(Set<ErrorCode> errorCodeList) {
        this.errorCodeList = errorCodeList;
    }
}
