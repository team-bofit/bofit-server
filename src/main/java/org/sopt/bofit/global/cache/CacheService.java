package org.sopt.bofit.global.cache;

import java.util.List;
import org.sopt.bofit.domain.post.entity.Post;

public interface CacheService {

    Object getData(String cacheName, Object key);

    public List<Post> getTrendingPosts();

}
