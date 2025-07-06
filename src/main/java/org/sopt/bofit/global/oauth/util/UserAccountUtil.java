package org.sopt.bofit.global.oauth.util;

import org.sopt.bofit.domain.user.entity.constant.Gender;

public class UserAccountUtil {
    public static Gender parseGender(String gender) {
        if (gender == null) return null;
        return gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE;
    }

    public static int parseBirthYear(String birthyear) {
        return (birthyear != null && birthyear.matches("\\d+")) ? Integer.parseInt(birthyear) : 0;
    }
}
