package org.sopt.bofit.global.exception;

import lombok.Getter;
import org.sopt.bofit.global.exception.constant.ErrorCode;


@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private final String messageDetail;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.messageDetail = null;
    }

    public CustomException(ErrorCode errorCode, String messageDetail){
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.messageDetail = messageDetail;
    }
}
