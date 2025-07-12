package org.sopt.bofit.domain.insurancereport.dto.response.surgery;

import org.sopt.bofit.domain.insurance.entity.benefit.SurgeryType;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record SurgeryTypeSection(
	CompareCoverage type1,
	CompareCoverage type2,
	CompareCoverage type3,
	CompareCoverage type4,
	CompareCoverage type5
) {
	public static SurgeryTypeSection of( SurgeryType product, SurgeryType average){
		return new SurgeryTypeSection(
			CompareCoverage.of(product.getType1(), average.getType1()),
			CompareCoverage.of(product.getType2(), average.getType2()),
			CompareCoverage.of(product.getType3(), average.getType3()),
			CompareCoverage.of(product.getType4(), average.getType4()),
			CompareCoverage.of(product.getType5(), average.getType5())
		);
	}
}
