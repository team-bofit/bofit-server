package org.sopt.bofit.global.exception.constant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InsuranceErrorCode implements ErrorCode{

	INVALID_COVERAGE_SELECTED_NUMBER(HttpStatus.BAD_REQUEST.value(), "잘못된 우선 순위 요구사항입니다."),
	NOT_FOUND_INSURANCE_TOTAL_AVERAGE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "전 연령 보험 보장 평균 데이터를 찾을 수 없습니다.")
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
