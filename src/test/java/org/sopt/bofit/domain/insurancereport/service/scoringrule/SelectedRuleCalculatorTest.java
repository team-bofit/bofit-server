package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.select.SelectedScoringRule;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.SelectedScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class SelectedRuleCalculatorTest extends IntegrationTestSupport {

	private final static double FIRST_WEIGHT = 1.2;
	private final static double SECOND_WEIGHT = 1.0;
	private final static double THIRD_WEIGHT = 0.8;

	@Autowired
	private SelectedRuleCalculator selectedRuleCalculator;

	@Autowired
	private SelectedScoringRuleRepository selectedScoringRuleRepository;

	@AfterEach
	void clearInAfterTest(){
		selectedScoringRuleRepository.deleteAllInBatch();
	}

	@DisplayName("선택된 우선 보장 항목에 해당하는 ScoringRule 점수를 정상적으로 계산함")
	@Test
	void calculateTest(){
		// given
		int productPremium = 10000;
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withPremium(productPremium)
			.withGeneralCancerDiagnosis(5000)
			.withGeneralCancerSurgery(1000)
			.withAtypicalCancerDiagnosis(1000)
			.build();

		SelectedScoringRule appliedRule1 = SelectedScoringRule.create(
			CoveragePreference.ESSENTIAL_ONLY,
			PREMIUM,
			ALWAYS_MULTIPLE,
			0,
			-0.0005d
		);

		SelectedScoringRule appliedRule2 = SelectedScoringRule.create(
			CoveragePreference.MAJOR_DISEASE,
			GENERAL_CANCER_DIAGNOSIS,
			GE,
			5000,
			10d
		);

		SelectedScoringRule appliedRule3 = SelectedScoringRule.create(
			CoveragePreference.MAJOR_DISEASE,
			GENERAL_CANCER_SURGERY,
			GE,
			1000,
			10d
		);

		SelectedScoringRule noneAppliedRule1 = SelectedScoringRule.create(
			CoveragePreference.MAJOR_DISEASE,
			ATYPICAL_CANCER_DIAGNOSIS,
			ConditionOperator.GE,
			5000,
			10d
		);

		selectedScoringRuleRepository.saveAll(List.of(
			appliedRule1,
			appliedRule2,
			appliedRule3,
			noneAppliedRule1
		));

		Map<CoveragePreference, Integer> selectedCoverages = Map.of(
			CoveragePreference.ESSENTIAL_ONLY, 1,
			CoveragePreference.SURGERY_COVERAGE, 2,
			CoveragePreference.MAJOR_DISEASE, 3
		);

		// when
		double result = selectedRuleCalculator.calculate(selectedCoverages, product);

		// then

		assertThat(result).isEqualTo(
			FIRST_WEIGHT * ScoringRuleUtil.getPoint(appliedRule1, product)  +
				THIRD_WEIGHT * ScoringRuleUtil.getPoint(appliedRule2, product) +
				THIRD_WEIGHT * ScoringRuleUtil.getPoint(appliedRule3, product) +
				THIRD_WEIGHT * ScoringRuleUtil.getPoint(noneAppliedRule1, product)
		);
	}

}