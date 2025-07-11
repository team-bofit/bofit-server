package org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization;

import org.sopt.bofit.domain.insurance.entity.benefit.DailyHospitalization;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

import com.sun.source.doctree.SeeTree;

public record DailyHospitalizationTotalSection(
	String additionalInfo,
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
			AdditionalInfo.HOSPITALIZATION.getInformation(),
			report.getDailyHospitalization().getDescription(),
			DailyHospitalizationSection.of(report.getDiseaseDailyHospitalization(),
				productDailyHospitalization.getDisease(), averageDailyHospitalization.getDisease()),
			DailyHospitalizationSection.of(report.getInjuryDailyHospitalization(),
				productDailyHospitalization.getInjury(), averageDailyHospitalization.getInjury())
		);
	}
}
