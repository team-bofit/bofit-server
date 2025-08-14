package org.sopt.bofit.domain.insurancereport.entity.constant;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;

class CoverageStatusTest {

	@DisplayName("포인트 평균별 CoverageStatus 가 정상적으로 반환됨")
	@TestFactory
	Collection<DynamicTest> judgeFromPoint(){

		return List.of(
			DynamicTest.dynamicTest("point 평균이 2를 넘는 경우 POWERFUL 이 나옴", ()-> {
				// when
				CoverageStatus result = CoverageStatus.judgeFromPoint(2.1);

				// then
				assertThat(result).isEqualTo(POWERFUL);
			}),
			DynamicTest.dynamicTest("point 평균이 1과 2 사이인 경우 ENOUGH 가 나옴", ()-> {
				// when
				CoverageStatus result = CoverageStatus.judgeFromPoint(1.3);

				// then
				assertThat(result).isEqualTo(ENOUGH);
			}),
			DynamicTest.dynamicTest("point 평균이 2인 경우 ENOUGH 가 나옴", ()-> {
				// when
				CoverageStatus result = CoverageStatus.judgeFromPoint(2);

				// then
				assertThat(result).isEqualTo(ENOUGH);
			}),
			DynamicTest.dynamicTest("point 평균이 1 이하인 경우 WEAKNESS 가 나옴", ()-> {
				// when
				CoverageStatus result = CoverageStatus.judgeFromPoint(0.8);

				// then
				assertThat(result).isEqualTo(WEAKNESS);
			}),
			DynamicTest.dynamicTest("point 평균이 1 인 경우 WEAKNESS 가 나옴", ()-> {
				// when
				CoverageStatus result = CoverageStatus.judgeFromPoint(1);

				// then
				assertThat(result).isEqualTo(WEAKNESS);
			})
		);
	}

	@DisplayName("강력, 강력, 충분, 부족인 경우 point 가 2.25 로 강력으로 판단됨")
	@Test
	void judgeFromStatuses(){
		// given
		List<CoverageStatus> statuses = List.of(POWERFUL, POWERFUL, ENOUGH, WEAKNESS);

		// when
		CoverageStatus result = judgeFromCoverageStatuses(statuses);

		// then
		assertThat(result).isEqualTo(POWERFUL);
	}

	@DisplayName("상품 보장 금액과 평균 보장 금액을 판단을 정상적으로 내림")
	@TestFactory
	Collection<DynamicTest> judgeFromCompareCoverage(){

		return List.of(
			DynamicTest.dynamicTest("상품의 보장 금액이 평균 보장 금액 보다 큰 경우 POWERFUL 이 나옴", ()-> {
				// given
				CompareCoverage compareCoverage = new CompareCoverage(2000, 1500);
				// when
				CoverageStatus result = CoverageStatus.judgeFromCompareCoverage(compareCoverage);
				// then
				assertThat(result).isEqualTo(POWERFUL);
			}),
			DynamicTest.dynamicTest("상품의 보장 금액이 평균 보장 금액과 동일한 경우 ENOUGH 가 나옴", ()-> {
				// given
				CompareCoverage compareCoverage = new CompareCoverage(2000, 2000);
				// when
				CoverageStatus result = CoverageStatus.judgeFromCompareCoverage(compareCoverage);
				// then
				assertThat(result).isEqualTo(ENOUGH);
			}),
			DynamicTest.dynamicTest("상품의 보장 금액이 평균 보장 금액 보다 작은 경우 WEAKNESS 가 나옴", ()-> {
				// given
				CompareCoverage compareCoverage = new CompareCoverage(2000, 2500);
				// when
				CoverageStatus result = CoverageStatus.judgeFromCompareCoverage(compareCoverage);
				// then
				assertThat(result).isEqualTo(WEAKNESS);
			})
		);
	}

	@DisplayName("상품 보장 금액과 평균 보장 금액을 비교하여 강력, 강력, 충분, 부족인 경우 point 가 2.25 로 강력으로 판단됨")
	@Test
	void judgeFromCompareCoverages(){
	    // given
		List<CompareCoverage> compareCoverages = List.of(
			new CompareCoverage(2000, 1500),
			new CompareCoverage(2000, 1800),
			new CompareCoverage(1000, 1000),
			new CompareCoverage(1000, 2000)
		);

		// when
		CoverageStatus result = CoverageStatus.judgeFromCompareCoverages(compareCoverages);

		// then
		assertThat(result).isEqualTo(POWERFUL);
	}


}