package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;

public record MajorDiseaseSubSection(
	CompareCoverage diagnosis,
	CompareCoverage injury
) {
	public static MajorDiseaseSubSection of(
		int diagnosisProductCoverage, int diagnosisAverageCoverage,
		int injuryProductCoverage, int injuryAverageCoverage
		){
		return new MajorDiseaseSubSection(
			new CompareCoverage(diagnosisProductCoverage, diagnosisAverageCoverage),
			new CompareCoverage(injuryProductCoverage, injuryAverageCoverage)
		);
	}
}
