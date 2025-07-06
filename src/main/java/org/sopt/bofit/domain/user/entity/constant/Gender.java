package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Gender {
    MALE, FEMALE;

    public static Optional<Gender> parseGender(String gender) {
        return Optional.ofNullable(gender)
                .map(String::toLowerCase)
                .map(g -> g.equals("male") ? Gender.MALE : Gender.FEMALE);
    }
}
