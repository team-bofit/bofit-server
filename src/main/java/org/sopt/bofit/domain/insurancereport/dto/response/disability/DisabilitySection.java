package org.sopt.bofit.domain.insurancereport.dto.response.disability;

import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record DisabilitySection (
	String coverageStatus,
	CompareCoverage coverage
){
	public static DisabilitySection of(CoverageStatus coverageStatus,
		int productCoverage, int averageCoverage){

		return new DisabilitySection(coverageStatus.getDescription(),
			CompareCoverage.of(productCoverage, averageCoverage));
	}
}
