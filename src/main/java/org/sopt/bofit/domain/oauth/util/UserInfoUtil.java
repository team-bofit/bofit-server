package org.sopt.bofit.domain.oauth.util;

import org.sopt.bofit.domain.user.entity.constant.Gender;

import java.time.MonthDay;

public class UserInfoUtil {
    public static Gender parseGender(String gender) {
        if (gender == null) return null;
        return gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE;
    }

    public static int parseBirthYear(String birthyear) {
        return (birthyear != null && birthyear.matches("\\d+")) ? Integer.parseInt(birthyear) : 0;
    }

    public static MonthDay parseBirthday(String birthday) {
        if (birthday == null || birthday.length() != 4) return null;
        int month = Integer.parseInt(birthday.substring(0, 2));
        int day = Integer.parseInt(birthday.substring(2, 4));
        return MonthDay.of(month, day);
    }
}
