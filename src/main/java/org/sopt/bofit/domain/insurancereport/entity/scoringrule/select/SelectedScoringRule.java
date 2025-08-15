package org.sopt.bofit.domain.insurancereport.entity.scoringrule.select;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;

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
public class SelectedScoringRule extends InsuranceScoringRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "selected_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CoveragePreference coveragePreference;

	public static SelectedScoringRule create(
		CoveragePreference coveragePreference,
		ConditionCoverage conditionCoverage,
		ConditionOperator conditionOperator,
		Integer conditionValue,
		Double point
	){
		SelectedScoringRule rule = SelectedScoringRule.builder()
			.coveragePreference(coveragePreference)
			.build();
		rule.conditionCoverage = conditionCoverage;
		rule.conditionOperator = conditionOperator;
		rule.conditionValue = conditionValue;
		rule.point = point;

		return rule;
	}
}
