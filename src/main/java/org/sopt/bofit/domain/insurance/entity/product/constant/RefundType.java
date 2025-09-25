package org.sopt.bofit.domain.insurance.entity.product.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RefundType {
    PROTECTION_ONLY("순수보장형"),
    PARTIAL_RETURN("일부환급형"),
    FULL_RETURN("만기환급형")
    ;

    private final String displayName;
}
