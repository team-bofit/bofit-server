package org.sopt.bofit.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
public class TestCacheConfig {

	@Bean
	@Primary
	public CacheManager mockCacheManager() {
		return new NoOpCacheManager();
	}
}
