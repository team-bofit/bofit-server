package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.sopt.bofit.domain.user.entity.constant.Job.*;

import java.util.List;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiseaseHistoryRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	public double calculate(List<DiagnosedDisease> diseaseHistory, InsuranceProduct product){

		if(diseaseHistory.isEmpty() || diseaseHistory.contains(DiagnosedDisease.NONE)) {
			return 0;
		}

		return diseaseHistory.stream()
			.flatMap(diagnosedDisease -> scoringRuleProvider.findAllDiseaseHistory(diagnosedDisease).stream())
			.mapToDouble(rule -> ScoringRuleUtil.getPoint(rule, product))
			.sum();
	}

}
