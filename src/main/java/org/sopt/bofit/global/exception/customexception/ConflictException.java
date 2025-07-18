package org.sopt.bofit.global.exception.customexception;

import org.sopt.bofit.global.exception.constant.ErrorCode;

public class ConflictException extends CustomException{
	public ConflictException(ErrorCode errorCode) {
		super(errorCode);
	}

	public ConflictException(ErrorCode errorCode, String messageDetail) {
		super(errorCode, messageDetail);
	}
}
