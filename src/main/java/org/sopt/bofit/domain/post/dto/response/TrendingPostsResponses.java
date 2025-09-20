package org.sopt.bofit.domain.post.dto.response;

import java.util.List;
import java.util.Map;
import org.sopt.bofit.domain.post.entity.Post;

public record TrendingPostsResponses (List<TrendingPostsResponse> posts){

    public static TrendingPostsResponses of(List<Post> trendingPosts, Map<Post, Boolean> isLiked){
        return new TrendingPostsResponses(trendingPosts.stream()
            .map(post -> TrendingPostsResponse.of(post, isLiked.get(post)))
            .toList());
    }
}
