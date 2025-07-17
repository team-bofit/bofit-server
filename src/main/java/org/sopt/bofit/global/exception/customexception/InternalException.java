package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class InternalException extends CustomException {
    public InternalException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InternalException(ErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
