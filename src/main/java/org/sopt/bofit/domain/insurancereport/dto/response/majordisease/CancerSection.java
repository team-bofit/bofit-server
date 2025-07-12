package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import org.sopt.bofit.domain.insurance.entity.benefit.Cancer;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record CancerSection(
	String additionalInfo,
	String coverageStatus,
	MajorDiseaseSubSection general,
	MajorDiseaseSubSection atypical
) {
	public static CancerSection of(CoverageStatus status, InsuranceProduct product, InsuranceBenefit average){
		Cancer productCancer = product.getMajorDisease().getCancer();
		Cancer averageCancer = average.getMajorDisease().getCancer();
		return new CancerSection(
			AdditionalInfo.CANCER.getInformation(),
			status.getDescription(),
			MajorDiseaseSubSection.of(productCancer.getGeneralDiagnosis(), averageCancer.getGeneralDiagnosis(),
				productCancer.getGeneralSurgery(), averageCancer.getGeneralSurgery()),
			MajorDiseaseSubSection.of(productCancer.getAtypicalDiagnosis(), averageCancer.getAtypicalDiagnosis(),
				productCancer.getAtypicalSurgery(), averageCancer.getAtypicalSurgery())
		);
	}
}