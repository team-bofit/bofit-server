
package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostCategoryFilter;
import org.sopt.bofit.domain.post.entity.constant.PostSortOrder;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReader {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostImageRepository postImageRepository;

    public SliceResponse<PostSummaryResponse, Long> getAllPosts(PostSortOrder order, PostCategoryFilter category,
                                                                Long userId, Long cursorId, int size){

        Slice<PostSummaryResponse> postList = postRepository.findAllByCursorId(order, category, userId, cursorId, size);

        return SliceResponse.from(postList);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
    }

    public Post getActiveById(Long postId) {
        return postRepository.findByIdAndStatus(postId, PostStatus.ACTIVE)
            .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
    }

    public SliceResponse<PostSummaryResponse, Long> findPostsByKeywordAndCursorId(Long userId, String keyword, Long cursorId, int size) {
        Slice<PostSummaryResponse> postList = postRepository.findAllByKeywordAndCursorId(userId, keyword, cursorId, size);

        return SliceResponse.from(postList);
    }
}
