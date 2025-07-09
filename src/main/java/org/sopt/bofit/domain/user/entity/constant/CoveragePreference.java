package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoveragePreference {
    MAJOR_DISEASE("암, 심장질환같은 큰 병에 대비"),
    DEATH_BENEFIT("가족에게 경제적 도움이 되는 보장"),
    ESSENTIAL_ONLY("꼭 필요한 보장만 챙기고 싶음"),
    ACCIDENT_PREDICTION("예기치 못한 사고에 대비"),
    SURGERY_COVERAGE("수술할 일이 생겼을 때 보장"),
    MAXIMUM_COVERAGE("폭넓은 보장을 받고 싶다"),
    RECOMMENDED_OPTION("많이 선택하는 걸로 설정")
    ;

    private final String description;
}
