package org.sopt.bofit.domain.insurancereport.dto.response;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;

import java.util.List;
import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;


import lombok.Builder;

public record InsuranceReportSummaryResponse (
	UUID insuranceReportId,
	String productName,
	String company,
	List<String> keywordChips,
	List<ShowCoverageStatus> statuses

){

	public static InsuranceReportSummaryResponse from(InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		return InsuranceReportSummaryResponse.builder()
			.insuranceReportId(report.getId())
			.company(product.getBasicInformation().getCompany())
			.productName(product.getBasicInformation().getName())
			.company(product.getBasicInformation().getCompany())
			.keywordChips(report.getReportRationale().getKeywordChips())
			.statuses(getMajorSevenCoverage(report))
			.build();
	}

	@Builder
	public InsuranceReportSummaryResponse(UUID insuranceReportId, String productName, String company,
		List<String> keywordChips, List<ShowCoverageStatus> statuses) {
		this.insuranceReportId = insuranceReportId;
		this.productName = productName;
		this.company = company;
		this.keywordChips = keywordChips;
		this.statuses = statuses;
	}

	private static List<ShowCoverageStatus> getMajorSevenCoverage(InsuranceReport report){
		return List.of(
			new ShowCoverageStatus(CANCER.getDisplayName(), report.getCancer().getDescription()),
			new ShowCoverageStatus(CEREBROVASCULAR.getDisplayName(), report.getCerebrovascular().getDescription()),
			new ShowCoverageStatus(HEART.getDisplayName(), report.getHeartDisease().getDescription()),
			new ShowCoverageStatus(SURGERY.getDisplayName(), report.getSurgery().getDescription()),
			new ShowCoverageStatus(HOSPITALIZATION.getDisplayName(), report.getDailyHospitalization().getDescription()),
			new ShowCoverageStatus(DISABILITY.getDisplayName(), report.getDisability().getDescription()),
			new ShowCoverageStatus(DEATH.getDisplayName(), report.getDeath().getDescription())
		);
	}
}
