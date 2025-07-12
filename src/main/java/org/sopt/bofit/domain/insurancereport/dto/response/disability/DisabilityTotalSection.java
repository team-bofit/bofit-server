package org.sopt.bofit.domain.insurancereport.dto.response.disability;

import org.sopt.bofit.domain.insurance.entity.benefit.Disability;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

public record DisabilityTotalSection(
	String additionalInfo,
	String coverageStatus,
	DisabilitySection disease,
	DisabilitySection injury
) {
	public static DisabilityTotalSection of(
		InsuranceReport report,
		InsuranceProduct product,
		InsuranceBenefit average
	){
		Disability productDisability = product.getDisability();
		Disability averageDisability = average.getDisability();
		return new DisabilityTotalSection(
			AdditionalInfo.DISABILITY.getInformation(),
			report.getDisability().getDescription(),
			DisabilitySection.of(report.getDiseaseDisability() ,
				productDisability.getDiseaseGE3PCT(), averageDisability.getDiseaseGE3PCT()),
			DisabilitySection.of(report.getInjuryDisability(),
				productDisability.getInjuryGE3PCT(), averageDisability.getInjuryGE3PCT())
		);
	}
}
