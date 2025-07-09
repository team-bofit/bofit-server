package org.sopt.bofit.domain.insurancereport.entity.scoringrule;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InsuranceScoringRule{

	@Enumerated(EnumType.STRING)
	private ConditionCoverage conditionCoverage;

	@Enumerated(EnumType.STRING)
	private ConditionOperator conditionOperator;

	private Integer conditionValue;

	private Double point;

}

