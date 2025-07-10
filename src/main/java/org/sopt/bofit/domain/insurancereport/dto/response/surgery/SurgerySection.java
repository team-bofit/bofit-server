package org.sopt.bofit.domain.insurancereport.dto.response.surgery;

import org.sopt.bofit.domain.insurance.entity.benefit.SurgeryType;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record SurgerySection(
	String coverageStatus,
	CompareCoverage surgery
) {
	public static SurgerySection of(
		CoverageStatus coverageStatus,
		SurgeryType product, SurgeryType average
	){
		return new SurgerySection(
			coverageStatus.getDescription(),
			CompareCoverage.of(product.getGeneral(), average.getGeneral())
		);
	}
}
