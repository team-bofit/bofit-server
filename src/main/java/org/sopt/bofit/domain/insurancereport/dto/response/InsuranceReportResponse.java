package org.sopt.bofit.domain.insurancereport.dto.response;

import java.util.List;
import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.benefit.Cancer;
import org.sopt.bofit.domain.insurance.entity.product.BasicInformation;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;

import lombok.Builder;

@Builder
public record InsuranceReportResponse (
	UUID reportId,
	BasicInformation reportInformation,
	ReportRationale reportRationale,
	SectionData majorDisease,
	SectionData surgery,
	SectionData hospitalization,
	SectionData disability,
	SectionData death
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
			.build();
	}


	private record SectionData(
		String additionalInfo,
		List<ShowCoverageStatus> statuses
	){
		private static SectionData majorDisease(InsuranceReport report){
			return new SectionData(
				AdditionalInfo.MAJOR_DISEASE.getInformation(),
				List.of(
					new ShowCoverageStatus(Disease.CANCER.getDisplayName(), report.getCancer().getDescription()),
					new ShowCoverageStatus(Disease.CEREBROVASCULAR.getDisplayName(), report.getCerebrovascular().getDescription()),
					new ShowCoverageStatus(Disease.HEART.getDisplayName(), report.getHeartDisease().getDescription())
				)
			);
		}

		private static SectionData surgery (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.SURGERY.getInformation(),
				List.of(
					new ShowCoverageStatus(Disease.DISEASE_SURGERY.getDisplayName(), report.getDiseaseSurgery().getDescription()),
					new ShowCoverageStatus(Disease.DISEASE_TYPE_SURGERY.getDisplayName(), report.getDiseaseTypeSurgery().getDescription()),
					new ShowCoverageStatus(Disease.INJURY_SURGERY.getDisplayName(), report.getInjurySurgery().getDescription()),
					new ShowCoverageStatus(Disease.INJURY_TYPE_SURGERY.getDisplayName(), report.getInjuryTypeSurgery().getDescription())
				)
			);
		}

		private static SectionData hospitalization (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.HOSPITALIZATION.getInformation(),
				List.of(
					new ShowCoverageStatus(Disease.DISEASE_DAILY_HOSPITALIZATION.getDisplayName(), report.getDiseaseDailyHospitalization().getDescription()),
					new ShowCoverageStatus(Disease.INJURY_DAILY_HOSPITALIZATION.getDisplayName(), report.getInjuryDailyHospitalization().getDescription())
				)
			);
		}

		private static SectionData disability (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.DISABILITY.getInformation(),
				List.of(
					new ShowCoverageStatus(Disease.DISEASE_DISABILITY.getDisplayName(), report.getDiseaseDisability().getDescription()),
					new ShowCoverageStatus(Disease.INJURY_DISABILITY.getDisplayName(), report.getInjuryDisability().getDescription())
				)
			);
		}

		private static SectionData death (InsuranceReport report){
			return new SectionData(
				AdditionalInfo.DEATH.getInformation(),
				List.of(
					new ShowCoverageStatus(Disease.DISEASE_DEATH.getDisplayName(), report.getDiseaseDeath().getDescription()),
					new ShowCoverageStatus(Disease.INJURY_DEATH.getDisplayName(), report.getInjuryDeath().getDescription())
				)
			);
		}

	}
}
