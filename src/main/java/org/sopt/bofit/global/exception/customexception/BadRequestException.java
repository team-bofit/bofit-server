package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class BadRequestException extends CustomException {
  public BadRequestException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public BadRequestException(ErrorCode errorCode){
    super(errorCode);
  }

}
