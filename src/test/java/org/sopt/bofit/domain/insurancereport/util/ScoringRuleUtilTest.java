package org.sopt.bofit.domain.insurancereport.util;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory.FamilyHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.select.SelectedScoringRule;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;

class ScoringRuleUtilTest {

	@DisplayName("GE 일 때 conditionValue 와 보장 금액이 동일한 경우 point를 정상적으로 반환함.")
	@Test
	void GEEqualConditionValue(){
	    // given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withGeneralCancerDiagnosis(5000)
			.build();

		SelectedScoringRule rule = SelectedScoringRule.create(
			CoveragePreference.MAJOR_DISEASE,
			GENERAL_CANCER_DIAGNOSIS,
			GE,
			5000,
			10d
		);

		// when
		double result = ScoringRuleUtil.getPoint(rule, product);

	    // then
		assertThat(result).isEqualTo(rule.getPoint());
	}

	@DisplayName("GE 일 때 보장 금액이 conditionValue 보다 큰 경우 point를 정상적으로 반환함.")
	@Test
	void productCoverConditionValueWhenGE(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withGeneralCancerDiagnosis(5000)
			.build();

		SelectedScoringRule rule = SelectedScoringRule.create(
			CoveragePreference.MAJOR_DISEASE,
			GENERAL_CANCER_DIAGNOSIS,
			GE,
			5000,
			10d
		);

		// when
		double result = ScoringRuleUtil.getPoint(rule, product);

		// then
		assertThat(result).isEqualTo(rule.getPoint());
	}

	@DisplayName("EXIST 일 때 보장 금액이 0 보다 큰 경우 point를 정상적으로 반환함.")
	@Test
	void productCoverConditionValueWhenEXIST(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withGeneralCancerDiagnosis(5000)
			.build();

		FamilyHistoryScoringRule rule = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			GENERAL_CANCER_DIAGNOSIS,
			EXIST,
			0,
			5d
		);

		// when
		double result = ScoringRuleUtil.getPoint(rule, product);

		// then
		assertThat(result).isEqualTo(rule.getPoint());
	}

	@DisplayName("ALWAYS_MULTIPLE 일 때 값을 곱하여 point를 정상적으로 반환함.")
	@Test
	void getPointWhenMINUS(){
		// given
		int premium = 73421;
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withPremium(premium)
			.build();

		SelectedScoringRule rule = SelectedScoringRule.create(
			CoveragePreference.ESSENTIAL_ONLY,
			PREMIUM,
			ALWAYS_MULTIPLE,
			0,
			-0.0005d
		);

		// when
		double result = ScoringRuleUtil.getPoint(rule, product);

		// then
		assertThat(result).isEqualTo(rule.getPoint() * premium);
	}


}