package org.sopt.bofit.domain.insurancereport.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Disease {

	MAJOR_DISEASE("큰병", "major-disease"),

	CANCER("암", "cancer"),
	GENERAL_CANCER("일반암", "general-cancer"),
	ATYPICAL_CANCER("소액암", "atypical-cancer"),

	CEREBROVASCULAR("뇌혈관질환", "cerebrovascular"),
	CEREBRAL_HEMORRHAGE("뇌출혈", "cerebral-hemorrhage"),
	CEREBRAL_INFARCTION("뇌경색", "cerebral-infarction"),
	OTHER_CEREBROVASCULAR("기타 뇌혈관질환", "other-cerebrovascular"),

	HEART("심장질환", "heart-disease"),
	ACUTE_MYOCARDIAL_INFARCTION("급성 심근경색", "acute-myocardial-infarction"),
	ARRHYTHMIA_HEART_FAILURE("부정맥, 심부전", "arrhythmia-heart-failure"),
	EXTENDED_HEART("확대 심장질환", "extended-heart-disease"),
	ISCHEMIC_HEART("허혈성 심장질환", "ischemic-heart-disease"),

	SURGERY("수술", "surgery"),
	DISEASE_SURGERY("질병수술비", "disease-surgery"),
	DISEASE_TYPE_SURGERY("질병 종 수술비", "disease-type-surgery"),
	INJURY_SURGERY("상해 수술", "injury-surgery"),
	INJURY_TYPE_SURGERY("상해 종 수술비", "injury-type-surgery"),

	HOSPITALIZATION("입원", "hospitalization"),
	DISEASE_DAILY_HOSPITALIZATION("질병입원일당(1일이상)", "disease-daily-hospitalization"),
	INJURY_DAILY_HOSPITALIZATION("상해입원일당(1일이상)", "injury-daily-hospitalization"),

	DISABILITY("장해", "disability"),
	DISEASE_DISABILITY("질병후유장해", "disease-disability"),
	INJURY_DISABILITY("상해후유장해", "injury-disability"),

	DEATH("사망", "death"),
	DISEASE_DEATH("질병사망", "disease-death"),
	INJURY_DEATH("상해사망", "injury-death")
	;

	private final String displayName;
	private final String hyphenCase;

}
