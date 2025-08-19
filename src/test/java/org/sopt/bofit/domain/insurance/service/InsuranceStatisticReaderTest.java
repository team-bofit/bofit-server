package org.sopt.bofit.domain.insurance.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.InsuranceStatisticTestBuilder;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.entity.statistic.StatisticRange;
import org.sopt.bofit.domain.insurance.repository.InsuranceStatisticRepository;
import org.sopt.bofit.global.exception.constant.InsuranceErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class InsuranceStatisticReaderTest extends IntegrationTestSupport {

	@Autowired
	private InsuranceStatisticReader insuranceStatisticReader;

	@Autowired
	private InsuranceStatisticRepository insuranceStatisticRepository;

	@AfterEach
	void clearInAfterTest(){
		insuranceStatisticRepository.deleteAllInBatch();
	}

	@DisplayName("TOTAL_AVERAGE 인 통계 정보가 존재하는 경우 정상적으로 가져옴")
	@Test
	void getTotalAverageWhenTotalAverageExist(){
	    // given
		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withDiseaseSurgery(10)
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.build();
		insuranceStatisticRepository.save(statistic);

	    // when
		InsuranceStatistic result = insuranceStatisticReader.getTotalAverage();

	    // then
		assertThat(result)
			.isNotNull()
			.isSameAs(result);

	}

	@DisplayName("TOTAL_AVERAGE 인 통계 정보가 존재하지 않는 경우 예외 발생.")
	@Test
	void getTotalAverageWhenTotalAverageNotExist(){
		// given & when & then
		assertThatThrownBy(() -> insuranceStatisticReader.getTotalAverage())
			.isInstanceOf(InternalException.class)
			.satisfies(e ->{
				InternalException exception = (InternalException)e;
				assertThat(exception.getErrorCode()).isEqualTo(InsuranceErrorCode.NOT_FOUND_INSURANCE_TOTAL_AVERAGE);
			});

	}
}