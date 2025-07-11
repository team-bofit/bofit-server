package org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public class UserInfoScoringRule extends InsuranceScoringRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_info_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private UserInfoRuleType userInfoRuleType;

}
