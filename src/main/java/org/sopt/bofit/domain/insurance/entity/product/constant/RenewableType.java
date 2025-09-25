package org.sopt.bofit.domain.insurance.entity.product.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RenewableType {

    RENEWABLE("갱신형"),
    NON_RENEWABLE("비갱신형")
    ;

    private final String displayName;
}
