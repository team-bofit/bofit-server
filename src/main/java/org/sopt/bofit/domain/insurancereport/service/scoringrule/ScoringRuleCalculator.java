package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoringRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	private final UserInfoRuleCalculator userInfoRuleCalculator;
	private final DiseaseHistoryRuleCalculator diseaseHistoryRuleCalculator;
	private final FamilyHistoryRuleCalculator familyHistoryRuleCalculator;
	private final SelectedRuleCalculator selectedRuleCalculator;

	public double calculatorScoringRule(
        PersonalInfo personalInfo,
		UserInfo userInfo,
		InsuranceProduct product,
		int age
	){
		return calculateUserInfoRule(personalInfo, product, age) +
			calculateDiseaseHistoryRule(userInfo, product) +
			calculateFamilyHistoryRule(userInfo, product) +
			calculateSelectedRule(userInfo, product);
	}

	public double calculateUserInfoRule(PersonalInfo personalInfo, InsuranceProduct product, int age){
		return userInfoRuleCalculator.calculate(personalInfo, product, age);
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
