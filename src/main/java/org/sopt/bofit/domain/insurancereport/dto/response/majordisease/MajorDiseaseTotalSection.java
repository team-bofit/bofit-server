package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

public record MajorDiseaseTotalSection(
	String additionalInfo,
	String coverageStatus,
	CancerSection cancerSection,
	CerebrovascularSection cerebrovascularSection,
	HeartSection heartSection
) {
	public static MajorDiseaseTotalSection of (InsuranceReport report, InsuranceProduct product, InsuranceStatistic average){
		return new MajorDiseaseTotalSection(
			AdditionalInfo.MAJOR_DISEASE.getInformation(),
			report.getMajorDisease().getDescription(),
			CancerSection.of(report.getCancer(), product, average),
			CerebrovascularSection.of(report.getCerebrovascular(), product, average),
			HeartSection.of(report.getHeartDisease(), product, average));
	}
}
