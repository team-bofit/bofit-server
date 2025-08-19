package org.sopt.bofit.domain.insurancereport.entity.scoringrule;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;

class ConditionCoverageTest {

	@DisplayName("conditionCoverage 에서 상품의 특정 필드값을 정상적으로 가져옴")
	@Test
	void getCoverageFromProduct(){
	    // given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withGeneralCancerDiagnosis(10000)
			.build();

		ConditionCoverage conditionCoverage = ConditionCoverage.GENERAL_CANCER_DIAGNOSIS;
	    // when
		int result = conditionCoverage.getCoverage(product);

		// then
		assertThat(result).isEqualTo(10000);
	}

	@DisplayName("ALL_DISEASE_SURGERY 에서 질병 5종 수술비와 질병 수슬비를 모두 보장하는 경우 값을 정상적으로 반환함")
	@Test
	void get_ALL_DISEASE_SURGERY_CoverageFromProduct(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withDiseaseSurgery(10000)
			.withDiseaseType5(10000)
			.build();

		ConditionCoverage conditionCoverage = ConditionCoverage.ALL_DISEASE_SURGERY;
		// when
		int result = conditionCoverage.getCoverage(product);

		// then
		assertThat(result).isEqualTo(20000);
	}

	@DisplayName("ALL_DISEASE_SURGERY 에서 질병 5종 수술비가 보장되지 않는 경우 0 원을 반환함")
	@Test
	void get_ALL_DISEASE_SURGERY_CoverageWithoutType5(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withDiseaseSurgery(10000)
			.build();

		ConditionCoverage conditionCoverage = ConditionCoverage.ALL_DISEASE_SURGERY;
		// when
		int result = conditionCoverage.getCoverage(product);

		// then
		assertThat(result).isEqualTo(0);
	}

	@DisplayName("ALL_DISEASE_SURGERY 에서 질병 수슬비가 보장되지 않는 경우 0 원을 반환함")
	@Test
	void get_ALL_DISEASE_SURGERY_CoverageWithoutDiseaseSurgery(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withDiseaseType5(10000)
			.build();

		ConditionCoverage conditionCoverage = ConditionCoverage.ALL_DISEASE_SURGERY;
		// when
		int result = conditionCoverage.getCoverage(product);

		// then
		assertThat(result).isEqualTo(0);
	}

}