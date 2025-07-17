package org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;

public record DailyHospitalizationSection (
	String displayName,
	String hyphenCase,
	CompareCoverage diseaseDailyHospitalization
){

	public static DailyHospitalizationSection getHospitalizationSection(String hyphenCase, InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		InsuranceStatistic statistic = report.getStatistic();

		if(DISEASE_DAILY_HOSPITALIZATION.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createDiseaseSection(product, statistic);
		if (INJURY_DAILY_HOSPITALIZATION.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createInjurySection(product, statistic);

		throw new BadRequestException(INVALID_REPORT_SECTION);
	}

	private static DailyHospitalizationSection createDiseaseSection(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		return new DailyHospitalizationSection(
			Disease.DISEASE_DAILY_HOSPITALIZATION.getDisplayName(),
			Disease.DISEASE_DAILY_HOSPITALIZATION.getHyphenCase(),
			CompareCoverage.of(product.getDailyHospitalization().getDisease(),
				average.getDailyHospitalization().getDisease()));
	}

	private static DailyHospitalizationSection createInjurySection(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		return new DailyHospitalizationSection(
			Disease.INJURY_DAILY_HOSPITALIZATION.getDisplayName(),
			Disease.INJURY_DAILY_HOSPITALIZATION.getHyphenCase(),
			CompareCoverage.of(product.getDailyHospitalization().getInjury(),
				average.getDailyHospitalization().getInjury()));
	}

}
