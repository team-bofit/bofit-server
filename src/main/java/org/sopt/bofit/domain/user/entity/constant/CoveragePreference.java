package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoveragePreference {
    MAJOR_DISEASE("암, 심장질환같은 큰 병에 대비하고 싶어요"),
    DEATH_BENEFIT("사망 시 가족에게 경제적 도움이 되는 보장을 원해요"),
    ESSENTIAL_ONLY("합리적인 보험료로 꼭 필요한 보장만 챙기고 싶어요"),
    ACCIDENT_PREDICTION("예기치 않은 사고에 대비하고 싶어요"),
    SURGERY_COVERAGE("수술할 일이 생겼을 때 보장받고 싶어요"),
    MAXIMUM_COVERAGE("가격이 좀 높아도 폭넓은 보장을 받고 싶어요"),
    RECOMMENDED_OPTION("잘 모르겠어요. 많이 선택하는 걸로 설정할래요")
    ;

    private final String description;
}
