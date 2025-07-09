package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.custom_exception.InternalException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoringRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	private final UserInfoRuleCalculator userInfoRuleCalculator;


	public double calculateUserInfoRule(User user, InsuranceProduct product){
		return userInfoRuleCalculator.calculate(user, product);
	}

}
