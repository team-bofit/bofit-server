package org.sopt.bofit.global.exception.customexception;

import lombok.Getter;
import org.sopt.bofit.global.exception.constant.ErrorCode;


@Getter
public abstract class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private final String messageDetail;

    protected CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.messageDetail = null;
    }

    protected CustomException(ErrorCode errorCode, String messageDetail) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.messageDetail = messageDetail;
    }
}
