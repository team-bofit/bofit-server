package org.sopt.bofit.domain.insurancereport.entity.scoringrule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConditionOperator {

	GE(">="),
	EXIST("> 0"),
	ALWAYS_MULTIPLE(""),
	;
	private final String operator;

}
