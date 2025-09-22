package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_NAME;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.dto.response.TrendingPostDto;
import org.sopt.bofit.global.cache.CacheService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCacheService {

    private final CacheService cacheService;

    public Map<Long, TrendingPostDto> getTrendPosts(){
        return cacheService.getTrendingPosts();
    }

    public boolean deletePost(Post post){
        if(getTrendPosts().containsKey(post.getId())){
            clearTrendingPostsCache();
            return true;
        }
        return false;
    }

    public void updatedPost(Post post){
        if(getTrendPosts().containsKey(post.getId())){
            clearTrendingPostsCache();
        }
    }

    public void increaseLikeCount(Long postId){
        Map<Long, TrendingPostDto> trendPosts = getTrendPosts();
        if(getTrendPosts().containsKey(postId)){
            getTrendPosts().get(postId).increaseLikeCount();
        }
    }

    public void decreaseLikeCount(Long postId){
        Map<Long, TrendingPostDto> trendPosts = getTrendPosts();
        if(getTrendPosts().containsKey(postId)){
            getTrendPosts().get(postId).decreaseLikeCount();
        }
    }

    public void increaseCommentCount(Long postId){
        if(getTrendPosts().containsKey(postId)){
            getTrendPosts().get(postId).increaseCommentCount();
        }
    }

    public void decreaseCommentCount(Long postId){
        if(getTrendPosts().containsKey(postId)){
            getTrendPosts().get(postId).decreaseCommentCount();
        }
    }

    private void clearTrendingPostsCache(){
        cacheService.deleteCache(TRENDING_POSTS_CACHE_NAME);
    }

}
