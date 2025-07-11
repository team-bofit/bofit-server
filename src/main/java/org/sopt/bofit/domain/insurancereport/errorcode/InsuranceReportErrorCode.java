package org.sopt.bofit.domain.insurancereport.errorcode;

import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InsuranceReportErrorCode implements ErrorCode {

	NOT_FOUND_INSURANCE_REPORT(HttpStatus.NOT_FOUND.value(), "보험 추천 리포트를 찾을 수 없습니다.")
	;

	private final int httpStatus;
	private final String message;

	@Override
	public int getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
