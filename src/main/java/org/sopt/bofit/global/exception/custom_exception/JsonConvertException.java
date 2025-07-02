package org.sopt.bofit.global.exception.custom_exception;

import org.sopt.bofit.global.exception.constant.GlobalErrorCode;

public class JsonConvertException extends CustomException {
    public JsonConvertException(GlobalErrorCode errorCode) {
        super(errorCode);
    }

    public JsonConvertException(GlobalErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
