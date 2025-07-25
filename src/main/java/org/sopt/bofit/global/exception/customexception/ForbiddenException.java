package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class ForbiddenException extends CustomException{
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ForbiddenException(ErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
