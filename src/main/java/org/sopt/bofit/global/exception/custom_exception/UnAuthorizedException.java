package org.sopt.bofit.global.exception.custom_exception;

import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;

public class UnAuthorizedException extends CustomException{
    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnAuthorizedException(ErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
