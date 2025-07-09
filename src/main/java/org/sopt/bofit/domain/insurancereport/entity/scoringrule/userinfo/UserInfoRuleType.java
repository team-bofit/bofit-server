package org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 현재는 해당 부분에서 조건이 많이 걸리지 않으므로 하나의 ENUM 으로 관리함.
 * 추후 해당 부분의 조건이 늘어나면 각 조건을 중분류로 분할이 필요
 */
@Getter
@RequiredArgsConstructor
public enum UserInfoRuleType {

	OLDER_THAN_50("50세 이상"),

	FEMALE("여성"),

	PRODUCTION_SITE("생산·현장직"),
	DRIVER_DELIVERY("운전·배달직"),

	MARRIED("기혼자"),
	DRIVER("운전자"),
	HAS_CHILD("자녀 존재"),
	;

	private final String description;
}
