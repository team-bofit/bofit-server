package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.user.dto.response.PostSummaryResponse;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {
    Slice<PostSummaryResponse> findPostsByCursorId(Long userId, Long cursorId, int size);
}
