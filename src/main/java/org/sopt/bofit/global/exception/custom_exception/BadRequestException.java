package org.sopt.bofit.global.exception.custom_exception;

import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.OAuthErrorCode;

public class BadRequestException extends CustomException {
  public BadRequestException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public BadRequestException(ErrorCode errorCode){
    super(errorCode);
  }

}
