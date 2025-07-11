package org.sopt.bofit.domain.insurancereport.dto.response.surgery;

import org.sopt.bofit.domain.insurance.entity.benefit.Surgery;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

public record SurgeryTotalSection(
	String coverageStatus,
	SurgerySection diseaseSurgerySection,
	SurgeryTypeSection diseaseSurgeryTypeSection,
	SurgerySection injurySurgerySection,
	SurgeryTypeSection injurySurgeryTypeSection

) {
	public static SurgeryTotalSection of(InsuranceReport report, InsuranceProduct product, InsuranceStatistic average){
		Surgery productSurgery = product.getSurgery();
		Surgery averageSurgery = average.getSurgery();

		return new SurgeryTotalSection(
			report.getSurgery().getDescription(),
			SurgerySection.of(report.getDiseaseSurgery(),
				productSurgery.getDiseaseSurgery(), averageSurgery.getInjurySurgery()),
			SurgeryTypeSection.of(report.getDiseaseSurgery(),
				productSurgery.getDiseaseSurgery(), averageSurgery.getDiseaseSurgery()),

			SurgerySection.of(report.getInjurySurgery(),
				productSurgery.getInjurySurgery(), averageSurgery.getInjurySurgery()),
			SurgeryTypeSection.of(report.getInjuryTypeSurgery(),
				productSurgery.getInjurySurgery(), averageSurgery.getInjurySurgery())
		);
	}

}
