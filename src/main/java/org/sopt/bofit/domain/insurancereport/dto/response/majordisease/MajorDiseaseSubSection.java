package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.Disease;

public record MajorDiseaseSubSection(
	String displayName,
	CompareCoverage diagnosis,
	CompareCoverage injury
) {
	public static MajorDiseaseSubSection of(
		Disease disease,
		int diagnosisProductCoverage, int diagnosisAverageCoverage,
		int injuryProductCoverage, int injuryAverageCoverage
	){
		return new MajorDiseaseSubSection(
			disease.getDisplayName(),
			new CompareCoverage(diagnosisProductCoverage, diagnosisAverageCoverage),
			new CompareCoverage(injuryProductCoverage, injuryAverageCoverage)
		);
	}
}
