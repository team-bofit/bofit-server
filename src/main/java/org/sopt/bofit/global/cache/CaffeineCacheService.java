package org.sopt.bofit.global.cache;


import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_KEY;
import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_NAME;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
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
    public List<Post> getTrendingPosts(){
        Object value = cacheManager.getCache(TRENDING_POSTS_CACHE_NAME).get(TRENDING_POSTS_CACHE_KEY, List.class);
        if (value != null &&
            value instanceof List list
        ){
            return (List<Post>) list;
        }
        return Collections.emptyList();
    }
}
