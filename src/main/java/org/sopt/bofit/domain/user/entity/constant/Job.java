package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Job {
    OFFICE_WORK("사무직"),
    SERVICE_SALES("서비스·영업직"),
    PROFESSIONAL("전문직"),
    SELF_EMPLOYED("자영업"),
    PRODUCTION_SITE("생산·현장직"),
    DRIVER_DELIVERY("운전·배달직"),
    HOMEMAKER("주부"),
    STUDENT("학생"),
    UNEMPLOYED("무직"),
    FREELANCER("프리랜서"),
    ETC("기타");

    private final String displayName;
}
