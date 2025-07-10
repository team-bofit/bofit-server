package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.diseasehistory.DiseaseHistoryScoringRule;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.custom_exception.InternalException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoringRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	private final UserInfoRuleCalculator userInfoRuleCalculator;
	private final DiseaseHistoryRuleCalculator diseaseHistoryRuleCalculator;
	private final FamilyHistoryRuleCalculator familyHistoryRuleCalculator;
	private final SelectedRuleCalculator selectedRuleCalculator;

	public double calculatorScoringRule(
		User user,
		UserInfo userInfo,
		InsuranceProduct product,
		int age
	){
		return calculateUserInfoRule(user, product, age) +
			calculateDiseaseHistoryRule(userInfo, product) +
			calculateFamilyHistoryRule(userInfo, product) +
			calculateSelectedRule(userInfo, product);
	}

	public double calculateUserInfoRule(User user, InsuranceProduct product, int age){
		return userInfoRuleCalculator.calculate(user, product, age);
	}

	public double calculateDiseaseHistoryRule(UserInfo userInfo, InsuranceProduct product){
		return diseaseHistoryRuleCalculator.calculate(userInfo.getDiseaseHistory(), product);
	}

	public double calculateFamilyHistoryRule(UserInfo userInfo, InsuranceProduct product){
		return familyHistoryRuleCalculator.calculate(userInfo.getFamilyHistory(), product);
	}

	public double calculateSelectedRule(UserInfo userInfo, InsuranceProduct product){
		return selectedRuleCalculator.calculate(userInfo.getCoveragePreferences(), product);
	}

}
