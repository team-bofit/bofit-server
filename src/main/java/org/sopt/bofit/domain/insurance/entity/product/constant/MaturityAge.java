package org.sopt.bofit.domain.insurance.entity.product.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MaturityAge {

    OLD_80(80, "80세"),
    OLD_90(90, "90세"),
    OLD_100(100, "100세")
    ;

    @JsonValue
    private final int age;
    private final String displayName;

    public static MaturityAge toMaturityAge(int age) {
        return Arrays.stream(MaturityAge.values())
            .filter(maturityAge -> maturityAge.getAge() == age)
            .findFirst()
            .orElse(null);
    }
}
