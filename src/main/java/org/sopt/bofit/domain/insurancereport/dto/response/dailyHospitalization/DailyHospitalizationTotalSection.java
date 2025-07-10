package org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization;

import org.sopt.bofit.domain.insurance.entity.benefit.DailyHospitalization;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

public record DailyHospitalizationTotalSection(
	String coverageStatus,
	DailyHospitalizationSection diseaseDailyHospitalization,
	DailyHospitalizationSection injuryDailyHospitalization
) {
	public static DailyHospitalizationTotalSection of(
		InsuranceReport report,
		InsuranceProduct product,
		InsuranceBenefit average
	){
		DailyHospitalization productDailyHospitalization = product.getDailyHospitalization();
		DailyHospitalization averageDailyHospitalization = average.getDailyHospitalization();
		return new DailyHospitalizationTotalSection(
			report.getDailyHospitalization().getDescription(),
			DailyHospitalizationSection.of(report.getDiseaseDailyHospitalization(),
				productDailyHospitalization.getDisease(), averageDailyHospitalization.getDisease()),
			DailyHospitalizationSection.of(report.getInjuryDailyHospitalization(),
				productDailyHospitalization.getInjury(), averageDailyHospitalization.getInjury())
		);
	}
}
