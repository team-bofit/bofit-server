package org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;

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
public class UserInfoScoringRule extends InsuranceScoringRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_info_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private UserInfoRuleType userInfoRuleType;

	public static UserInfoScoringRule create(
		UserInfoRuleType userInfoRuleType,
		ConditionCoverage conditionCoverage,
		ConditionOperator conditionOperator,
		Integer conditionValue,
		Double point
	){
		UserInfoScoringRule rule = UserInfoScoringRule.builder()
			.userInfoRuleType(userInfoRuleType)
			.build();
		rule.conditionCoverage = conditionCoverage;
		rule.conditionOperator = conditionOperator;
		rule.conditionValue = conditionValue;
		rule.point = point;

		return rule;
	}

}
