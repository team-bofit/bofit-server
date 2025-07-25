package org.sopt.bofit.domain.insurancereport.dto.response.death;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.global.exception.customexception.BadRequestException;

public record DeathSection(
	String displayName,
	String hyphenCase,
	CompareCoverage coverage
) {

	public static DeathSection getDeathSection(String hyphenCase, InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		InsuranceStatistic statistic = report.getStatistic();

		if (DISEASE_DEATH.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createDisease(product, statistic);
		if (INJURY_DEATH.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createInjury(product, statistic);

		throw new BadRequestException(INVALID_REPORT_SECTION);
	}

	private static DeathSection createDisease(
		InsuranceProduct product, InsuranceStatistic statistic
	){
		return new DeathSection(
			DISEASE_DEATH.getDisplayName(),
			DISEASE_DEATH.getHyphenCase(),
			CompareCoverage.of(product.getDeath().getDisease(), statistic.getDeath().getDisease()));
	}

	private static DeathSection createInjury(
		InsuranceProduct product, InsuranceStatistic statistic
	){
		return new DeathSection(
			INJURY_DEATH.getDisplayName(),
			INJURY_DEATH.getHyphenCase(),
			CompareCoverage.of(product.getDeath().getInjury(), statistic.getDeath().getInjury()));
	}
}
