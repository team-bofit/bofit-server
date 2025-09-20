 package org.sopt.bofit.global.constant;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheConstant {

	public final static String DEFAULT_CACHE_NAME = "default";
	public final static String USER_INFO_SCORING_RULE_CACHE_NAME = "userInfoScoringRule";
	public final static String DISEASE_HISTORY_SCORING_RULE_CACHE_NAME = "diseaseHistoryScoringRule";
	public final static String FAMILY_HISTORY_SCORING_RULE_CACHE_NAME = "familyHistoryScoringRule";
	public final static String SELECTED_SCORING_RULE_CACHE_NAME = "selectedScoringRule";

	public final static String INSURANCE_STATISTIC_CACHE_NAME = "insuranceStatistic";
	public final static String INSURANCE_REPORT_CACHE_NAME = "insuranceReport";

    public final static String TRENDING_POSTS_CACHE_NAME = "trendingPosts";
    public final static String TRENDING_POSTS_CACHE_KEY = "all";

}
