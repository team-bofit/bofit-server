package org.sopt.bofit.domain.post.dto.response;

import java.util.List;
import java.util.Map;
import org.sopt.bofit.domain.post.service.dto.response.TrendingPostDto;

public record TrendingPostsResponses (List<TrendingPostsResponse> posts){

    public static TrendingPostsResponses of(List<TrendingPostDto> trendingPosts, Map<Long, Boolean> isLiked){
        return new TrendingPostsResponses(trendingPosts.stream()
            .map(post -> TrendingPostsResponse.of(post, isLiked.get(post.getPostId())))
            .toList());
    }
}
