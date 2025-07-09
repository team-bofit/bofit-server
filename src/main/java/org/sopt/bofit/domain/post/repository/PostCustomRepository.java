package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.user.dto.response.MyPostsResponse;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {
    Slice<MyPostsResponse> findPostsByCursorId(Long userId, Long cursorId, int size);
}
