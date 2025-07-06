package org.sopt.bofit.domain.insurance.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InsuranceStatus {

	SELLING("판매중"),
	EXPIRED("만료됨")
	;
	private final String description;
}
