package org.sopt.bofit.domain.insurance.entity.product.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentPeriod {
    YEAR_10(10, "10년"),
    YEAR_20(20, "20년"),
    YEAR_30(30, "30년")
    ;

    private final int year;
    private final String displayName;

    public static PaymentPeriod convertToEntityAttribute(Integer dbData) {
        switch (dbData) {
            case 10: return YEAR_10;
            case 20: return YEAR_20;
            case 30: return YEAR_30;
            default: return null;
        }
    }
}
