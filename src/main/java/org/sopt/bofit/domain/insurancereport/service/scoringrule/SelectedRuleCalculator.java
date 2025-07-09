package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import java.util.List;
import java.util.Map;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.select.SelectedScoringRule;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.global.exception.constant.InsuranceErrorCode;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelectedRuleCalculator {
	private final ScoringRuleProvider scoringRuleProvider;

	private final static double FIRST_WEIGHT = 1.2;
	private final static double SECOND_WEIGHT = 1.0;
	private final static double THIRD_WEIGHT = 0.8;

	public double calculate(Map<CoveragePreference, Integer> coveragePreferences, InsuranceProduct product){
		if(coveragePreferences.isEmpty()) {
			return 0;
		}
		
		return coveragePreferences.entrySet()
			.stream()
			.mapToDouble(entry -> {
				List<SelectedScoringRule> rules = scoringRuleProvider.findAllCoveragePreference(entry.getKey());
				return pointFromCoveragePreference(product, entry, rules);
			}).sum();
	}

	private double pointFromCoveragePreference(
		InsuranceProduct product, Map.Entry<CoveragePreference, Integer> entry, List<SelectedScoringRule> rules) {
		return this.resolveWeight(entry.getValue()) * rules.stream()
			.mapToDouble(rule -> ScoringRuleUtil.getPoint(rule, product))
			.sum();
	}
	
	private double resolveWeight(Integer weight){
		return switch (weight){
			case 1 -> FIRST_WEIGHT;
			case 2 -> SECOND_WEIGHT;
			case 3 -> THIRD_WEIGHT;
			default -> throw new BadRequestException(InsuranceErrorCode.NOT_FOUND_INSURANCE_TOTAL_AVERAGE);
		};
	}

}
