package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_CALCULATE_SIZE;
import static org.sopt.bofit.global.constant.CacheConstant.TRENDING_POSTS_CACHE_NAME;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.TrendPost;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.post.repository.TrendPostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrendPostWriter {

    private final TrendPostRepository trendPostRepository;
    private final PostRepository postRepository;

    private final PostCacheService postCacheService;

    @Scheduled(
        fixedDelayString = "${scheduler.trend-posts-update-delay}"
    )
    @CacheEvict(cacheNames = TRENDING_POSTS_CACHE_NAME, allEntries = true)
    @Transactional
    public void updateTrendPosts(){
        List<Post> trendedPosts = postRepository.findTrendPosts(TREND_POST_CALCULATE_SIZE, LocalDateTime.now());
        List<TrendPost> trendPosts = IntStream.range(0, trendedPosts.size())
            .mapToObj(rank -> TrendPost.create(trendedPosts.get(rank), rank + 1))
            .toList();

        trendPostRepository.deleteAllInBatch();
        trendPostRepository.saveAll(trendPosts);
    }

    public void validDeletePost(Post post){
        if(postCacheService.deletePost(post)){
            Optional<TrendPost> deletedTrendPost = trendPostRepository.findByPost(post);
            deletedTrendPost.ifPresent(trendPostRepository::delete);
        }
    }

    public void validUpdatedPost(Post post){
        postCacheService.updatedPost(post);
    }

}
