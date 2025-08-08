package org.sopt.bofit.domain.insurancereport.entity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Disease {

	MAJOR_DISEASE("큰 병", "major-disease", InsuranceReport::getMajorDisease),

	CANCER("암", "cancer", InsuranceReport::getCancer),
	GENERAL_CANCER("일반암", "general-cancer", report -> null),
	ATYPICAL_CANCER("소액암", "atypical-cancer", report -> null),

	CEREBROVASCULAR("뇌혈관질환", "cerebrovascular", InsuranceReport::getCerebrovascular),
	CEREBRAL_HEMORRHAGE("뇌출혈", "cerebral-hemorrhage", report -> null),
	CEREBRAL_INFARCTION("뇌경색", "cerebral-infarction", report -> null),
	OTHER_CEREBROVASCULAR("기타 뇌혈관질환", "other-cerebrovascular", report -> null),

	HEART("심장질환", "heart-disease", InsuranceReport::getHeartDisease),
	ACUTE_MYOCARDIAL_INFARCTION("급성 심근경색", "acute-myocardial-infarction", report -> null),
	ARRHYTHMIA_HEART_FAILURE("부정맥, 심부전", "arrhythmia-heart-failure", report -> null),
	EXTENDED_HEART("확대 심장질환", "extended-heart-disease", report -> null),
	ISCHEMIC_HEART("허혈성 심장질환", "ischemic-heart-disease", report -> null),

	SURGERY("수술", "surgery", InsuranceReport::getSurgery),
	DISEASE_SURGERY("질병수술비", "disease-surgery", InsuranceReport::getDiseaseSurgery),
	DISEASE_TYPE_SURGERY("질병 종 수술비", "disease-type-surgery", InsuranceReport::getDiseaseTypeSurgery),
	INJURY_SURGERY("상해수술비", "injury-surgery", InsuranceReport::getInjurySurgery),
	INJURY_TYPE_SURGERY("상해 종 수술비", "injury-type-surgery", InsuranceReport::getInjuryTypeSurgery),

	HOSPITALIZATION("입원", "hospitalization", InsuranceReport::getDailyHospitalization),
	DISEASE_DAILY_HOSPITALIZATION("질병입원일당(1일이상)", "disease-daily-hospitalization", InsuranceReport::getDiseaseDailyHospitalization),
	INJURY_DAILY_HOSPITALIZATION("상해입원일당(1일이상)", "injury-daily-hospitalization", InsuranceReport::getInjuryDailyHospitalization),

	DISABILITY("장해", "disability", InsuranceReport::getDisability),
	DISEASE_DISABILITY("질병후유장해", "disease-disability", InsuranceReport::getDiseaseDisability),
	INJURY_DISABILITY("상해후유장해", "injury-disability", InsuranceReport::getInjuryDisability),

	DEATH("사망", "death", InsuranceReport::getDeath),
	DISEASE_DEATH("질병사망", "disease-death", InsuranceReport::getDiseaseDeath),
	INJURY_DEATH("상해사망", "injury-death", InsuranceReport::getInjuryDeath)
	;

	private final String displayName;
	private final String hyphenCase;
	private final Function<InsuranceReport,CoverageStatus> coverageStatusFromReport;

	public static final Map<Disease, List<Disease>> diseaseSectionMap = Map.of(
		MAJOR_DISEASE, getMajorDiseaseSections(),
		SURGERY, getSurgerySections(),
		HOSPITALIZATION, getHospitalizationSections(),
		DISABILITY, getDisabilitySections(),
		DEATH, getDeathSections()
	);

	public static List<Disease> getMajorDiseaseSections(){
		return List.of(
			CANCER,
			CEREBROVASCULAR,
			HEART
		);
	}

	public static List<Disease> getSurgerySections(){
		return List.of(
			DISEASE_SURGERY,
			DISEASE_TYPE_SURGERY,
			INJURY_SURGERY,
			INJURY_TYPE_SURGERY
		);
	}

	public static List<Disease> getHospitalizationSections(){
		return List.of(
			DISEASE_DAILY_HOSPITALIZATION,
			INJURY_DAILY_HOSPITALIZATION
		);
	}

	public static List<Disease> getDisabilitySections(){
		return List.of(
			DISEASE_DISABILITY,
			INJURY_DISABILITY
		);
	}

	public static List<Disease> getDeathSections(){
		return List.of(
			DISEASE_DEATH,
			INJURY_DEATH
		);
	}

}
