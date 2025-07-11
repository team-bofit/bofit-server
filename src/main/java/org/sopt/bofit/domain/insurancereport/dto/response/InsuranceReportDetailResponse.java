package org.sopt.bofit.domain.insurancereport.dto.response;

import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.product.BasicInformation;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization.DailyHospitalizationTotalSection;
import org.sopt.bofit.domain.insurancereport.dto.response.death.DeathSection;
import org.sopt.bofit.domain.insurancereport.dto.response.death.DeathTotalSection;
import org.sopt.bofit.domain.insurancereport.dto.response.disability.DisabilitySection;
import org.sopt.bofit.domain.insurancereport.dto.response.disability.DisabilityTotalSection;
import org.sopt.bofit.domain.insurancereport.dto.response.majordisease.MajorDiseaseTotalSection;
import org.sopt.bofit.domain.insurancereport.dto.response.surgery.SurgeryTotalSection;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;

public record InsuranceReportDetailResponse (
 	UUID reportId,
	Long productId,
	BasicInformation basicInformation,
	ReportRationale rationale,
	MajorDiseaseTotalSection majorDiseaseTotalSection,
	SurgeryTotalSection surgeryTotalSection,
	DailyHospitalizationTotalSection dailyHospitalizationTotalSection,
	DisabilityTotalSection disabilityTotalSection,
	DeathTotalSection deathTotalSection
) {

	public static InsuranceReportDetailResponse of(
		InsuranceReport report,
		InsuranceProduct product,
		InsuranceStatistic average
	){
		return new InsuranceReportDetailResponse(
			report.getId(),
			product.getId(),
			product.getBasicInformation(),
			report.getReportRationale(),
			MajorDiseaseTotalSection.of(report, product, average),
			SurgeryTotalSection.of(report, product, average),
			DailyHospitalizationTotalSection.of(report, product, average),
			DisabilityTotalSection.of(report, product, average),
			DeathTotalSection.of(report, product, average)
		);
	}

}
