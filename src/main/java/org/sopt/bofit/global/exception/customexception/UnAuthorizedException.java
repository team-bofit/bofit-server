package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class UnAuthorizedException extends CustomException{
    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnAuthorizedException(ErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
