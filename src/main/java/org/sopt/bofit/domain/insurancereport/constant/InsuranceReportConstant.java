package org.sopt.bofit.domain.insurancereport.constant;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsuranceReportConstant {

	public final static String NAME_REGEX = "^[가-힣]+$";

	public final static List<String> DEFAULT_RATIONALE_REASONS = List.of(
		"고객님의 상황을 고려해 최적의 상품을 추천해드렸어요",
		"다시 보험 추천 리포트를 받으면 더욱 잘 추천해드릴 수 있어요"
		);

	public final static List<String> DEFAULT_RATIONAL_KEYWORD_CHIPS = List.of(
		"합리적인 보험료",
		"맞춤형 보장 범위"
	);

}
