package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiagnosedDisease {
    CANCER("암",null),
    CEREBROVASCULAR("뇌혈관질환","뇌출혈, 뇌경색"),
    HEART("심장질환",null),
    RESPIRATORY("호흡기질환", null),
    RIVER("간질환", null),
    KIDNEY("신장질환", null),
    MENTAL("정신질환", null),
    CHRONIC("만성질환", "고혈압, 당뇨 등"),
    NONE("해당 없음", null)
    ;


    private final String diseaseName;
    private final String description;
}
