package org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamilyHistoryScoringRule extends InsuranceScoringRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "family_history_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private DiagnosedDisease diagnosedDisease;

	public static FamilyHistoryScoringRule create(
		DiagnosedDisease diagnosedDisease,
		ConditionCoverage conditionCoverage,
		ConditionOperator conditionOperator,
		Integer conditionValue,
		Double point
	){
		FamilyHistoryScoringRule rule = FamilyHistoryScoringRule.builder()
			.diagnosedDisease(diagnosedDisease)
			.build();
		rule.conditionCoverage = conditionCoverage;
		rule.conditionOperator = conditionOperator;
		rule.conditionValue = conditionValue;
		rule.point = point;

		return rule;
	}
}
