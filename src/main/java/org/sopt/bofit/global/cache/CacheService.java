package org.sopt.bofit.global.cache;

import java.util.Map;
import org.sopt.bofit.domain.post.service.dto.response.TrendingPostDto;

public interface CacheService {

    Object getData(String cacheName, Object key);

    Map<Long, TrendingPostDto> getTrendingPosts();

    void deleteCache(String cacheName);
}
