package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;

@Getter
public enum Gender {
    MALE, FEMALE;

    public static Gender parseGender(String gender) {
        if (gender == null) return null;
        return gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE;
    }
}
