package org.sopt.bofit.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성")
    ;

    private final String displayName;


    public static Optional<Gender> parseGender(String gender) {
        return Optional.ofNullable(gender)
                .map(String::toLowerCase)
                .map(g -> g.equals("male") ? Gender.MALE : Gender.FEMALE);
    }
}
