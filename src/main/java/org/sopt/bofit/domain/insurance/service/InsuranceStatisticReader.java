package org.sopt.bofit.domain.insurance.service;

import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.entity.statistic.StatisticRange;
import org.sopt.bofit.domain.insurance.repository.InsuranceStatisticRepository;
import org.sopt.bofit.global.exception.constant.InsuranceErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceStatisticReader {
	private final InsuranceStatisticRepository insuranceStatisticRepository;

	public InsuranceStatistic getTotalAverage (){
		return insuranceStatisticRepository.findByStatisticRange(StatisticRange.TOTAL_AVERAGE).orElseThrow(() ->
			new InternalException(InsuranceErrorCode.NOT_FOUND_INSURANCE_TOTAL_AVERAGE));
	}

}
