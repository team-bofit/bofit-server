package org.sopt.bofit.domain.post.entity.constant;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TrendPostSort {
    RANK("rank", "순위"),
    RANDOM("random", "무작위")
    ;

    private final String lowerCase;
    private final String description;

    public static TrendPostSort from(String value){
        return Arrays.stream(values())
            .filter(trendPostSort -> trendPostSort.lowerCase.equalsIgnoreCase(value))
            .findFirst()
            .orElse(RANK);
    }
}
