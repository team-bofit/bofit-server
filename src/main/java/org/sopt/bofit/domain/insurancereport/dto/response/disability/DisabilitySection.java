package org.sopt.bofit.domain.insurancereport.dto.response.disability;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;

public record DisabilitySection (
	String displayName,
	CompareCoverage coverage
){
	public static DisabilitySection getDisabilitySection(String hyphenCase, InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		InsuranceStatistic statistic = report.getStatistic();

		if (DISEASE_DISABILITY.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createDisease(product, statistic);
		if (INJURY_DISABILITY.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createInjury(product, statistic);

		throw new BadRequestException(INVALID_REPORT_SECTION);
	}

	private static DisabilitySection createDisease (
		InsuranceProduct product, InsuranceStatistic statistic){

		int productDiseaseGE3PCT = product.getDisability().getDiseaseGE3PCT();
		int statisticDiseaseGE3PCT = statistic.getDisability().getDiseaseGE3PCT();

		return new DisabilitySection(
			DISEASE_DISABILITY.getDisplayName(),
			CompareCoverage.of(productDiseaseGE3PCT, statisticDiseaseGE3PCT));
	}

	private static DisabilitySection createInjury (
		InsuranceProduct product, InsuranceStatistic statistic){

		int productInjuryGE3PCT = product.getDisability().getInjuryGE3PCT();
		int statisticInjuryGE3PCT = statistic.getDisability().getInjuryGE3PCT();

		return new DisabilitySection(
			INJURY_DISABILITY.getDisplayName(),
			CompareCoverage.of(productInjuryGE3PCT, statisticInjuryGE3PCT));
	}


	public static DisabilitySection of(CoverageStatus coverageStatus,
		int productCoverage, int averageCoverage){

		return new DisabilitySection(coverageStatus.getDescription(),
			CompareCoverage.of(productCoverage, averageCoverage));
	}
}
