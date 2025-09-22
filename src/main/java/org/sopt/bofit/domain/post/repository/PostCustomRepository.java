package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostCategoryFilter;
import org.sopt.bofit.domain.post.entity.constant.PostSortOrder;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface PostCustomRepository {
    Slice<MyPostSummaryResponse> findPostsByCursorId(Long userId, Long cursorId, int size);

    void deletePostByPostId(Long postId);

    Slice<PostSummaryResponse> findAllByCursorId(PostSortOrder order, PostCategoryFilter category, Long userId, Long cursorId, int size);

    Slice<PostSummaryResponse> findAllByKeywordAndCursorId(Long userId, String keyword, Long cursorId, int size);

    List<Post> findTrendPosts(int size, LocalDateTime now);

}
