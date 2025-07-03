package org.sopt.bofit.global.exception.custom_exception;

import org.sopt.bofit.global.exception.constant.GlobalErrorCode;

public class InternalException extends CustomException {
    public InternalException(GlobalErrorCode errorCode) {
        super(errorCode);
    }

    public InternalException(GlobalErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
