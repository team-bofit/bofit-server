package org.sopt.bofit.domain.insurancereport.dto.response.death;

import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record DeathSection(
	String coverageStatus,
	CompareCoverage coverage
) {
	public static DeathSection of(CoverageStatus coverageStatus,
		int productCoverage, int averageCoverage){
		return new DeathSection(coverageStatus.getDescription(),
			CompareCoverage.of(productCoverage, averageCoverage));
	}

}
