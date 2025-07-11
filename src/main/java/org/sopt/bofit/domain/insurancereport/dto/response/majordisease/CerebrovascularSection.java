package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import org.sopt.bofit.domain.insurance.entity.benefit.Cerebrovascular;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record CerebrovascularSection(
	String additionalInfo,
	String coverageStatus,
	MajorDiseaseSubSection hemorrhage,
	MajorDiseaseSubSection infarction,
	MajorDiseaseSubSection other
) {

	public static CerebrovascularSection of(CoverageStatus status, InsuranceProduct product, InsuranceBenefit average){
		Cerebrovascular productCerebrovascular = product.getMajorDisease().getCerebrovascular();
		Cerebrovascular averageCerebrovascular = average.getMajorDisease().getCerebrovascular();

		return new CerebrovascularSection(
			AdditionalInfo.CEREBROVASCULAR.getInformation(),
			status.getDescription(),
			MajorDiseaseSubSection.of(
				productCerebrovascular.getHemorrhageDiagnosis(), averageCerebrovascular.getHemorrhageDiagnosis(),
				productCerebrovascular.getHemorrhageSurgery(), averageCerebrovascular.getHemorrhageSurgery()),
			MajorDiseaseSubSection.of(
				productCerebrovascular.getInfarctionDiagnosis(), averageCerebrovascular.getInfarctionDiagnosis(),
				productCerebrovascular.getInfarctionSurgery(), averageCerebrovascular.getInfarctionSurgery()),
			MajorDiseaseSubSection.of(
				productCerebrovascular.getOtherDiagnosis(), averageCerebrovascular.getOtherDiagnosis(),
				productCerebrovascular.getOtherSurgery(), averageCerebrovascular.getOtherSurgery())
			);
	}
}
