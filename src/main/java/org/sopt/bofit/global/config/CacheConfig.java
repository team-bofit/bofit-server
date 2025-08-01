package org.sopt.bofit.global.config;

import static org.sopt.bofit.global.constant.CacheConstant.*;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(Caffeine.newBuilder()
			.initialCapacity(100)
			.maximumSize(500)
			.expireAfterAccess(30, TimeUnit.MINUTES));
		cacheManager.setCacheNames(java.util.Arrays.asList(DEFAULT_CACHE_NAME)); // 캐시 이름 지정
		cacheManager.registerCustomCache(USER_INFO_SCORING_RULE_CACHE_NAME, scoringRuleCacheBuilder().build());
		cacheManager.registerCustomCache(DISEASE_HISTORY_SCORING_RULE_CACHE_NAME, scoringRuleCacheBuilder().build());
		cacheManager.registerCustomCache(FAMILY_HISTORY_SCORING_RULE_CACHE_NAME, scoringRuleCacheBuilder().build());
		cacheManager.registerCustomCache(SELECTED_SCORING_RULE_CACHE_NAME, scoringRuleCacheBuilder().build());

		cacheManager.registerCustomCache(INSURANCE_REPORT_CACHE_NAME, insuranceReportCacheBuilder().build());
		cacheManager.registerCustomCache(INSURANCE_STATISTIC_CACHE_NAME, insuranceStatisticCacheBuilder().build());
		return cacheManager;
	}

	private Caffeine<Object, Object> scoringRuleCacheBuilder(){
		return Caffeine.newBuilder()
			.expireAfterWrite(1, TimeUnit.DAYS)
			.maximumSize(200)
			.softValues()
			;
	}

	private Caffeine<Object, Object> insuranceReportCacheBuilder(){
		return Caffeine.newBuilder()
			.expireAfterAccess(60, TimeUnit.MINUTES)
			.maximumSize(50)
			.softValues()
			// .recordStats()
			;
	}

	private Caffeine<Object, Object> insuranceStatisticCacheBuilder(){
		return Caffeine.newBuilder()
			.expireAfterWrite(1, TimeUnit.DAYS)
			.maximumSize(10)
			.softValues()
			;
	}
}