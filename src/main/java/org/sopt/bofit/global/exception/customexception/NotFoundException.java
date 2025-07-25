package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class NotFoundException extends CustomException {

  public NotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public NotFoundException(ErrorCode errorCode, String messageDetail) {
    super(errorCode, messageDetail);
  }
}
