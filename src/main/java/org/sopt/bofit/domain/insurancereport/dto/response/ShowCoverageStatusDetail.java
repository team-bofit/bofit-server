package org.sopt.bofit.domain.insurancereport.dto.response;

import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

import lombok.Builder;

public record ShowCoverageStatusDetail(
	String target,
	String status,
	String queryParamValue
){

	@Builder
	public ShowCoverageStatusDetail(String target, String status, String queryParamValue) {
		this.target = target;
		this.status = status;
		this.queryParamValue = queryParamValue;
	}

	public static ShowCoverageStatusDetail create(Disease disease, InsuranceReport report){
		return ShowCoverageStatusDetail.builder()
			.target(disease.getDisplayName())
			.status(disease.getCoverageStatusFromReport()
				.apply(report)
				.getDescription())
			.queryParamValue(disease.getHyphenCase())
			.build();
		// return new ShowCoverageStatus(disease.getDisplayName(), disease.getCoverageStatusFromReport(report) report)
	}

}
