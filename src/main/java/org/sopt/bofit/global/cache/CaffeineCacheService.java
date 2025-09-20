package org.sopt.bofit.global.cache;


import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_KEY;
import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_NAME;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.service.dto.response.TrendingPostDto;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CaffeineCacheService implements CacheService {

    private final CacheManager cacheManager;

    @Override
    public Object getData(String cacheName, Object key){
        return cacheManager.getCache(cacheName).get(key);
    }

    @Override
    @SuppressWarnings("unchecked")  // TRENDING_POSTS_CACHE_NAME 캐시는 항상 Post 객체를 담고 있으로 안전함.
    public Map<Long, TrendingPostDto> getTrendingPosts(){

        Object value = cacheManager.getCache(TRENDING_POSTS_CACHE_NAME).get(TRENDING_POSTS_CACHE_KEY,  LinkedHashMap.class);

        if (value != null &&
            value instanceof LinkedHashMap map
        ){
            return (LinkedHashMap<Long, TrendingPostDto>) map;
        }
        return Collections.emptyMap();
    }

    @Override
    public void deleteCache(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }
}
