package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import java.util.List;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyHistoryRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	public double calculate(List<DiagnosedDisease> familyHistory, InsuranceProduct product){

		if(familyHistory.isEmpty() || familyHistory.contains(DiagnosedDisease.NONE)) {
			return 0;
		}

		return familyHistory.stream()
			.flatMap(diagnosedDisease -> scoringRuleProvider.findAllFamilyHistory(diagnosedDisease).stream())
			.mapToDouble(rule -> ScoringRuleUtil.getPoint(rule, product))
			.sum();
	}
}
