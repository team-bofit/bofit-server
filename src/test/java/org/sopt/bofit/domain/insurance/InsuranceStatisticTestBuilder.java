package org.sopt.bofit.domain.insurance;

import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.entity.statistic.StatisticRange;

public class InsuranceStatisticTestBuilder extends InsuranceBenefitTestBuilder<InsuranceStatisticTestBuilder, InsuranceStatistic> {

	private StatisticRange statisticRange = StatisticRange.TOTAL_AVERAGE;

	public InsuranceStatisticTestBuilder withStatisticRange(StatisticRange statisticRange) {
		this.statisticRange = statisticRange;
		return this;
	}

	@Override
	public InsuranceStatistic build() {
		InsuranceStatistic statistic = InsuranceStatistic.builder()
			.statisticRange(statisticRange)
			.build();

		applyCommonFields(statistic);

		return statistic;
	}
}
