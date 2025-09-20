package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_KEY;
import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_NAME;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.TrendPost;
import org.sopt.bofit.domain.post.repository.TrendPostRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendPostReader {

    private final TrendPostRepository trendPostRepository;

    public List<TrendPost> getAll(){
        return trendPostRepository.findAll();
    }

    @Cacheable(cacheNames = TRENDING_POSTS_CACHE_NAME, key = TRENDING_POSTS_CACHE_KEY)
    public List<Post> getTrendingPosts(List<TrendPost> trendPosts){
        return trendPostRepository.findAllPostsByTrendPosts(trendPosts);
    }

}
