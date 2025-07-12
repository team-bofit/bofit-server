package org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.dto.response.majordisease.MajorDiseaseSection;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;

public record DailyHospitalizationSection (
	String displayName,
	CompareCoverage diseaseDailyHospitalization
){

	public static DailyHospitalizationSection getHospitalizationSection(String hyphenCase, InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		InsuranceStatistic statistic = report.getStatistic();

		if(DISEASE_DAILY_HOSPITALIZATION.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createDisease(product, statistic);
		if (INJURY_DAILY_HOSPITALIZATION.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createInjury(product, statistic);

		throw new BadRequestException(INVALID_REPORT_SECTION);
	}

	private static DailyHospitalizationSection createDisease(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		return new DailyHospitalizationSection(
			Disease.DISEASE_DAILY_HOSPITALIZATION.getDisplayName(),
			CompareCoverage.of(product.getDailyHospitalization().getDisease(),
				average.getDailyHospitalization().getDisease()));
	}

	private static DailyHospitalizationSection createInjury(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		return new DailyHospitalizationSection(
			Disease.INJURY_DAILY_HOSPITALIZATION.getDisplayName(),
			CompareCoverage.of(product.getDailyHospitalization().getInjury(),
				average.getDailyHospitalization().getInjury()));
	}

}
