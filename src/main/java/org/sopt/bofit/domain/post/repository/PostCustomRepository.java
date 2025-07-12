package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.dto.response.PostListResponse;
import org.sopt.bofit.domain.user.dto.response.PostSummaryResponse;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {
    Slice<PostSummaryResponse> findPostsByCursorId(Long userId, Long cursorId, int size);

    void deletePostByPostId(Long postId);

    Slice<PostListResponse> findAllByCursorId(Long cursorId, int size);
}
