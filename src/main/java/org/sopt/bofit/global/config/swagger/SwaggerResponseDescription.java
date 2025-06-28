package org.sopt.bofit.global.config.swagger;

import lombok.Getter;
import org.sopt.bofit.global.exception.constant.ErrorCode;

import java.util.Set;


@Getter
public enum SwaggerResponseDescription {
    ;
    private final Set<ErrorCode> errorCodeList;
    SwaggerResponseDescription(Set<ErrorCode> errorCodeList) {
        this.errorCodeList = errorCodeList;
    }
}
