package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class S3Exception extends CustomException {

    public S3Exception(ErrorCode errorCode) {
        super(errorCode);
    }

    public S3Exception(ErrorCode errorCode, String messageDetail) {
        super(errorCode, messageDetail);
    }
}
