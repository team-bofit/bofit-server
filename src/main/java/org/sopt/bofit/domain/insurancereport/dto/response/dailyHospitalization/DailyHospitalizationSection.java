package org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization;

import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record DailyHospitalizationSection (
	String coverageStatus,
	CompareCoverage diseaseDailyHospitalization
){
	public static DailyHospitalizationSection of(
		CoverageStatus coverageStatus,
		int productCoverage,
		int averageCoverage
	){
		return new DailyHospitalizationSection(
			coverageStatus.getDescription(),
			new CompareCoverage(productCoverage, averageCoverage));
	}
}
