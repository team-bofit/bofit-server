package org.sopt.bofit.domain.insurancereport.entity.scoringrule;

import java.util.List;
import java.util.function.Function;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;
import org.sopt.bofit.global.exception.custom_exception.InternalException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConditionCoverage {

	DAILY_HOSPITALIZATION_DISEASE("질병 입원 일당", product -> product.getDailyHospitalization().getDisease()),
	DAILY_HOSPITALIZATION_INJURY("상해 입원 일당", product -> product.getDailyHospitalization().getInjury()),


	DISEASE_DEATH("질병 사망", product -> product.getDeath().getDisease()),
	INJURY_DEATH("상해 사망", product -> product.getDeath().getInjury()),


	DISEASE_DISABILITY_GE_3_PCT("질병 장애 3% 이상", product -> product.getDisability().getDiseaseGE3PCT()),
	INJURY_DISABILITY_GE_3_PCT("상해 장애 3% 이상", product -> product.getDisability().getInjuryGE3PCT()),


	ATYPICAL_CANCER_DIAGNOSIS("소액암 진단", product -> product.getMajorDisease().getCancer().getAtypicalDiagnosis()),
	ATYPICAL_CANCER_SURGERY("소액암 수술", product -> product.getMajorDisease().getCancer().getAtypicalSurgery()),

	GENERAL_CANCER_DIAGNOSIS("일반암 진단", product -> product.getMajorDisease().getCancer().getGeneralDiagnosis()),
	GENERAL_CANCER_SURGERY("일반암 수술", product -> product.getMajorDisease().getCancer().getGeneralSurgery()),


	CEREBRAL_HEMORRHAGE_DIAGNOSIS("뇌혈관 질환 진단", product -> product.getMajorDisease().getCerebrovascular().getHemorrhageDiagnosis()),
	CEREBRAL_HEMORRHAGE_SURGERY("뇌혈관 질환 수술", product -> product.getMajorDisease().getCerebrovascular().getHemorrhageSurgery()),

	CEREBRAL_INFARCTION_DIAGNOSIS("뇌경색 진단", product -> product.getMajorDisease().getCerebrovascular().getInfarctionDiagnosis()),
	CEREBRAL_INFARCTION_SURGERY("뇌경색 수술", product -> product.getMajorDisease().getCerebrovascular().getInfarctionSurgery()),

	OTHER_CEREBROVASCULAR_DIAGNOSIS("기타 뇌혈관 질환 진단", product -> product.getMajorDisease().getCerebrovascular().getOtherDiagnosis()),
	OTHER_CEREBROVASCULAR_SURGERY("기타 뇌혈관 질환 수술", product -> product.getMajorDisease().getCerebrovascular().getOtherSurgery()),


	ACUTE_MYOCARDIAL_INFARCTION_DIAGNOSIS("급성 심근경색 진단", product -> product.getMajorDisease().getHeart().getAcuteMyocardialInfarctionDiagnosis()),
	ACUTE_MYOCARDIAL_INFARCTION_SURGERY("급성 심근경색 수술", product -> product.getMajorDisease().getHeart().getAcuteMyocardialInfarctionSurgery()),

	ARRHYTHMIA_HEART_FAILURE_DIAGNOSIS("부정맥 심부전 진단", product -> product.getMajorDisease().getHeart().getArrhythmiaDiagnosis()),
	ARRHYTHMIA_HEART_FAILURE_SURGERY("부정맥 심부전 수술", product -> product.getMajorDisease().getHeart().getArrhythmiaSurgery()),

	EXTENDED_HEART_DIAGNOSIS("확대 심장 질환 진단", product -> product.getMajorDisease().getHeart().getExtendedDiagnosis()),
	EXTENDED_HEART_DISEASE_SURGERY("확대 심장 질환 수술", product -> product.getMajorDisease().getHeart().getExtendedSurgery()),

	ISCHEMIC_HEART_DISEASE_DIAGNOSIS("허혈성 심장 질환 진단", product -> product.getMajorDisease().getHeart().getIschemicDiagnosis()),
	ISCHEMIC_HEART_DISEASE_SURGERY("허혈성 심장 질환 수술", product -> product.getMajorDisease().getHeart().getIschemicSurgery()),


	DISEASE_SURGERY("질병 수술비", product -> product.getSurgery().getDisease()),
	DISEASE_TYPE_1_SURGERY("질병 1종 수술비", product -> product.getSurgery().getDiseaseType1()),
	DISEASE_TYPE_2_SURGERY("질병 2종 수술비", product -> product.getSurgery().getDiseaseType2()),
	DISEASE_TYPE_3_SURGERY("질병 3종 수술비", product -> product.getSurgery().getDiseaseType3()),
	DISEASE_TYPE_4_SURGERY("질병 4종 수술비", product -> product.getSurgery().getDiseaseType4()),
	DISEASE_TYPE_5_SURGERY("질병 5종 수술비", product -> product.getSurgery().getDiseaseType5()),
	ALL_DISEASE_SURGERY("질병 수술비 및 질병 종 수술비 전부", product -> List.of(product.getSurgery().getDisease(), product.getSurgery().getDiseaseType5())),


	INJURY_SURGERY("상해 수술", product -> product.getSurgery().getInjury()),
	INJURY_TYPE_1_SURGERY("상해 1종 수술비", product -> product.getSurgery().getInjuryType1()),
	INJURY_TYPE_2_SURGERY("상해 2종 수술비", product -> product.getSurgery().getInjuryType2()),
	INJURY_TYPE_3_SURGERY("상해 3종 수술비", product -> product.getSurgery().getInjuryType3()),
	INJURY_TYPE_4_SURGERY("상해 4종 수술비", product -> product.getSurgery().getInjuryType4()),
	INJURY_TYPE_5_SURGERY("상해 5종 수술비", product -> product.getSurgery().getInjuryType5()),

	PRICE("보험료", product -> product.getBasicInformation().getPremium())
	;
	private final String description;
	private final Function<InsuranceProduct, Object> fieldAccessor;

	public int getCoverage(InsuranceProduct product){
		try {
			return (int)fieldAccessor.apply(product);
		}catch (Exception e){
			throw new InternalException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "보장 금액 파싱 중 오류");
		}
	}

}
