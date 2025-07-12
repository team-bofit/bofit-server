package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {
    Slice<MyPostSummaryResponse> findPostsByCursorId(Long userId, Long cursorId, int size);

    void deletePostByPostId(Long postId);

    Slice<PostSummaryResponse> findAllByCursorId(Long cursorId, int size);
}
