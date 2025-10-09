package org.sopt.bofit.domain.post.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrendPostConstant {

    public static final String TREND_POST_DEFAULT_SIZE = "3";
    public final static String TREND_POST_DEFAULT_SORT = "rank";

    public static final int TREND_POST_SCORED_DATE_RANGE = 7;

    public static final int TREND_POST_CALCULATE_SIZE = 20;

    public static final int TREND_POST_COMMENT_WEIGHT = 1;
    public static final int TREND_POST_LIKE_WEIGHT = 1;
    public static final int TREND_POST_COMMENT_REPLY_WEIGHT = 2;

}
