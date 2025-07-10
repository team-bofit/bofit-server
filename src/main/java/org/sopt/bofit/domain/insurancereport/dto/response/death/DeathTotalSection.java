package org.sopt.bofit.domain.insurancereport.dto.response.death;

import org.sopt.bofit.domain.insurance.entity.benefit.Death;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;

public record DeathTotalSection(
	String coverageStatus,
	DeathSection disease,
	DeathSection injury
) {
	public static DeathTotalSection of(
		InsuranceReport report,
		InsuranceProduct product,
		InsuranceBenefit average){
		Death productDeath = product.getDeath();
		Death averageDeath = average.getDeath();

		return new DeathTotalSection(
			report.getDeath().getDescription(),
			DeathSection.of(report.getDiseaseDeath(), productDeath.getDisease(), averageDeath.getDisease()),
			DeathSection.of(report.getInjuryDeath(), productDeath.getInjury(), averageDeath.getInjury())
		);
	}
}
