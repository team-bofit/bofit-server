package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import org.sopt.bofit.domain.insurance.entity.benefit.Heart;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

public record HeartSection(
	String additionalInfo,
	String coverageStatus,
	MajorDiseaseSubSection acuteMyocardialInfarction,
	MajorDiseaseSubSection ischemic,
	MajorDiseaseSubSection extended,
	MajorDiseaseSubSection arrhythmia
) {
	public static HeartSection of(CoverageStatus status, InsuranceProduct product, InsuranceBenefit average){
		Heart productHeart = product.getMajorDisease().getHeart();
		Heart averageHeart = average.getMajorDisease().getHeart();

		return new HeartSection(
			AdditionalInfo.HEART_DISEASE.getInformation(),
			status.getDescription(),
			MajorDiseaseSubSection.of(
				productHeart.getAcuteMyocardialInfarctionDiagnosis(), averageHeart.getAcuteMyocardialInfarctionDiagnosis(),
				productHeart.getAcuteMyocardialInfarctionSurgery(), averageHeart.getAcuteMyocardialInfarctionSurgery()),
			MajorDiseaseSubSection.of(
				productHeart.getIschemicDiagnosis(), averageHeart.getIschemicDiagnosis(),
				productHeart.getIschemicSurgery(), averageHeart.getIschemicSurgery()),
			MajorDiseaseSubSection.of(
				productHeart.getExtendedDiagnosis(), averageHeart.getExtendedDiagnosis(),
				productHeart.getExtendedSurgery(), averageHeart.getExtendedSurgery()),
			MajorDiseaseSubSection.of(
				productHeart.getArrhythmiaDiagnosis(), averageHeart.getArrhythmiaDiagnosis(),
				productHeart.getArrhythmiaSurgery(), averageHeart.getArrhythmiaSurgery())
		);
	}
}
