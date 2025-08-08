package org.sopt.bofit.domain.insurancereport.dto.response;

import java.util.List;
import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.benefit.Cancer;
import org.sopt.bofit.domain.insurance.entity.product.BasicInformation;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;

@Builder
@JsonPropertyOrder({"reportId", "reportInformation", "reportRationale",
	"majorDisease", "surgery", "hospitalization", "disability", "death", "externalUri"})
public record InsuranceReportResponse (
	UUID reportId,
	BasicInformation reportInformation,
	ReportRationale reportRationale,
	SectionData majorDisease,
	SectionData surgery,
	SectionData hospitalization,
	SectionData disability,
	SectionData death,
	String externalUri
) {

	public static InsuranceReportResponse from(InsuranceReport report){
		return InsuranceReportResponse.builder()
			.reportId(report.getId())
			.reportInformation(report.getProduct().getBasicInformation())
			.reportRationale(report.getReportRationale())
			.majorDisease(SectionData.majorDisease(report))
			.surgery(SectionData.surgery(report))
			.hospitalization(SectionData.hospitalization(report))
			.disability(SectionData.disability(report))
			.death(SectionData.death(report))
			.externalUri(report.getProduct().getExtraInformation().getExternalUri())
			.build();
	}


	private record SectionData(
		String additionalInfo,
		String resource,
		List<ShowCoverageStatusDetail> statuses
	){
		private static SectionData majorDisease(InsuranceReport report){
			return new SectionData(
				AdditionalInfo.MAJOR_DISEASE.getInformation(),
				Disease.MAJOR_DISEASE.getHyphenCase(),
				Disease.getMajorDiseaseSections().stream()
					.map(disease -> ShowCoverageStatusDetail.create(disease, report))
					.toList()
			);
		}

		private static SectionData surgery (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.SURGERY.getInformation(),
				Disease.SURGERY.getHyphenCase(),
				Disease.getSurgerySections().stream()
					.map(disease -> ShowCoverageStatusDetail.create(disease, report))
					.toList()
			);
		}

		private static SectionData hospitalization (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.HOSPITALIZATION.getInformation(),
				Disease.HOSPITALIZATION.getHyphenCase(),
				Disease.getHospitalizationSections().stream()
					.map(disease -> ShowCoverageStatusDetail.create(disease, report))
					.toList()
			);
		}

		private static SectionData disability (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.DISABILITY.getInformation(),
				Disease.DISABILITY.getHyphenCase(),
				Disease.getDisabilitySections().stream()
					.map(disease -> ShowCoverageStatusDetail.create(disease, report))
					.toList()
			);
		}

		private static SectionData death (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.DEATH.getInformation(),
				Disease.DEATH.getHyphenCase(),
				Disease.getDeathSections().stream()
					.map(disease -> ShowCoverageStatusDetail.create(disease, report))
					.toList()
			);
		}

	}
}
