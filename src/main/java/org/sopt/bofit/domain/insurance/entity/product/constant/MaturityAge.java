package org.sopt.bofit.domain.insurance.entity.product.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MaturityAge {

    OLD_80(80, "80세"),
    OLD_100(100, "100세")
    ;

    private final int age;
    private final String displayName;

    public static MaturityAge toMaturityAge(int age) {
        switch (age){
            case 80: return OLD_80;
            case 100: return OLD_100;
            default: return null;
        }
    }
}
