package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.global.constant.CacheConstant.SPEL_TRENDING_POSTS_CACHE_KEY;
import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_NAME;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.TrendPost;
import org.sopt.bofit.domain.post.repository.TrendPostRepository;
import org.sopt.bofit.domain.post.service.dto.response.TrendingPostDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendPostReader {

    private final TrendPostRepository trendPostRepository;

    public List<TrendPost> getAll(){
        return trendPostRepository.findAll();
    }

    @Cacheable(cacheNames = TRENDING_POSTS_CACHE_NAME, key = SPEL_TRENDING_POSTS_CACHE_KEY)
    public LinkedHashMap<Long, TrendingPostDto> getTrendingPosts(){

        List<TrendPost> trendPosts = trendPostRepository.findAll();

        return trendPostRepository.findAllPostsByTrendPosts(trendPosts).stream()
            .collect(Collectors.toMap(
                Post::getId,
                TrendingPostDto::from,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));
    }

}
