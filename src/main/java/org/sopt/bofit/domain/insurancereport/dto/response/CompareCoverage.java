package org.sopt.bofit.domain.insurancereport.dto.response;

public record CompareCoverage(
	int productCoverage,
	int averageCoverage
) {

	public static CompareCoverage of(int productCoverage, int averageCoverage){
		return new CompareCoverage(productCoverage, averageCoverage);
	}
}
