package org.sopt.bofit.domain.insurancereport.entity.scoringrule;

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
	protected ConditionCoverage conditionCoverage;

	@Enumerated(EnumType.STRING)
	protected ConditionOperator conditionOperator;

	protected Integer conditionValue;

	protected Double point;

}

