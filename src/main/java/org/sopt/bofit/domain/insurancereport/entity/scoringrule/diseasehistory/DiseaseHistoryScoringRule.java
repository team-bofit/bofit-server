package org.sopt.bofit.domain.insurancereport.entity.scoringrule.diseasehistory;

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
public class DiseaseHistoryScoringRule extends InsuranceScoringRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "disease_history_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private DiagnosedDisease diagnosedDisease;

	public static DiseaseHistoryScoringRule create(
		DiagnosedDisease diagnosedDisease,
		ConditionCoverage conditionCoverage,
		ConditionOperator conditionOperator,
		Integer conditionValue,
		Double point
	){
		DiseaseHistoryScoringRule rule = DiseaseHistoryScoringRule.builder()
			.diagnosedDisease(diagnosedDisease)
			.build();
		rule.conditionCoverage = conditionCoverage;
		rule.conditionOperator = conditionOperator;
		rule.conditionValue = conditionValue;
		rule.point = point;

		return rule;
	}
}
