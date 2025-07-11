package org.sopt.bofit.domain.insurancereport.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdditionalInfo {

	MAJOR_DISEASE("큰병", "3대 중증질환(암, 뇌혈관, 심장)은 발병률이 높고, 치료비 부담도 커요. 진단 즉시 목돈(진단비)이 지급돼 경제적 부담을 줄여줘요."),
	CANCER("암", "일반암(위암, 폐암 등)과 소액암(갑상선암, 전립선암 등)으로 나뉘며, 치료비가 저렴하고 완치가 쉽기 때문에 보장금액이 낮게 설정돼요."),

	CEREBROVASCULAR("뇌혈관질환", "뇌출혈→뇌경색→뇌혈관질환 순으로 보장 범위가 넓어져요. 범위가 넓을수록 더 다양한 질환을 보장받을 수 있어요."),
	HEART_DISEASE("심장질환", " 심근경색보다 허혈성심장질환(협심증, 급성심근경색 포함)이 더 넓은 보장이에요. 확대심장질환과 심부전, 부정맥 특약도 고려해볼 수 있어요."),
	SURGERY("수술", "예상치 못한 수술에 대비해 수술비를 보장해요. 종수술비는 수술 종류에 따라 금액이 달라지며, 숫자가 클수록 위험도가 높고 보장도 커져요."),
	HOSPITALIZATION("입원", "입원 시 하루당 일정 금액이 지급돼 병원비와 소득 손실 부담을 덜어줘요. 대부분 연간 180일, 1회 입원당 120일 한도가 있으니 꼭 확인하세요."),
	DISABILITY("장해", "질병이나 사고로 인한 영구적 신체 손상을 보장해요. 보통 장해지급률 3% 이상부터 해당하며, 3%는 시력 상실, 10%는 손가락 절단 등이 포함돼요."),
	DEATH("사망", "사망 시 가족에게 지급되는 보장금으로, 생활비와 장례비에 도움이 돼요. 질병사망과 상해사망으로 구분되며, 상해사망은 사고 위험을 반영해 보장 금액이 더 높아요.")
	;

	private final String description;
	private final String information;
}


