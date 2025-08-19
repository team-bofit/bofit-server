package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory.FamilyHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.FamilyHistoryScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class FamilyHistoryRuleCalculatorTest extends IntegrationTestSupport {

	@Autowired
	private FamilyHistoryRuleCalculator familyHistoryRuleCalculator;

	@Autowired
	private FamilyHistoryScoringRuleRepository familyHistoryScoringRuleRepository;

	@AfterEach
	void clearInAfterTest(){
		familyHistoryScoringRuleRepository.deleteAllInBatch();
	}

	@DisplayName("가족력과 관련된 ScoringRule 점수를 정상적으로 계산한다.")
	@Test
	void calculateTest(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withGeneralCancerDiagnosis(5000)
			.withGeneralCancerSurgery(1200)
			.withDailyHospitalizationDisease(50)
			.withIschemicDiagnosis(1000)
			.build();

		FamilyHistoryScoringRule appliedRule1 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			GENERAL_CANCER_DIAGNOSIS,
			GE,
			5000,
			5d
		);

		FamilyHistoryScoringRule appliedRule2 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			GENERAL_CANCER_SURGERY,
			GE,
			1000,
			5d
		);

		FamilyHistoryScoringRule appliedRule3 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CHRONIC,
			DAILY_HOSPITALIZATION_DISEASE,
			EXIST,
			0,
			3d
		);

		FamilyHistoryScoringRule noneAppliedRule1 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.HEART,
			ISCHEMIC_HEART_DISEASE_DIAGNOSIS,
			ConditionOperator.GE,
			3000,
			10d
		);

		familyHistoryScoringRuleRepository.saveAll(List.of(
			appliedRule1,
			appliedRule2,
			appliedRule3,
			noneAppliedRule1
		));

		List<DiagnosedDisease> diagnosedDiseases = List.of(
			DiagnosedDisease.CANCER,
			DiagnosedDisease.CHRONIC,
			DiagnosedDisease.HEART
		);

		// when
		double result = familyHistoryRuleCalculator.calculate(diagnosedDiseases, product);

		// then
		assertThat(result).isEqualTo(
			ScoringRuleUtil.getPoint(appliedRule1, product)  +
				ScoringRuleUtil.getPoint(appliedRule2, product) +
				ScoringRuleUtil.getPoint(appliedRule3, product) +
				ScoringRuleUtil.getPoint(noneAppliedRule1, product)
		);
	}
}