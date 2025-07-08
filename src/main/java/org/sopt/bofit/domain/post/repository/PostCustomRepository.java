package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.user.dto.response.MyPostsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {
    Slice<MyPostsResponse> findMyPosts(Long userId, Pageable pageable);
}
