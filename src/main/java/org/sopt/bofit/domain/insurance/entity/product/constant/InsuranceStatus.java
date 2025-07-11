package org.sopt.bofit.domain.insurance.entity.product.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InsuranceStatus {

	RECOMMENDED("추천 상품"),
	SELLING("판매중"),
	EXPIRED("만료됨")
	;
	private final String description;
}
