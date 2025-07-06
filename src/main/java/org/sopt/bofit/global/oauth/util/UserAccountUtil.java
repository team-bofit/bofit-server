package org.sopt.bofit.global.oauth.util;

import org.sopt.bofit.domain.user.entity.constant.Gender;

public class UserAccountUtil {
    public static Gender parseGender(String gender) {
        if (gender == null) return null;
        return gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE;
    }

    public static int parseBirth(String birthday) {
        return (birthday != null && birthday.matches("\\d+")) ? Integer.parseInt(birthday) : 0;
    }


}
