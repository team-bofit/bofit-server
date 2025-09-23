
package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.dto.response.PostCategoryResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostCategoryFilter;
import org.sopt.bofit.domain.post.entity.constant.PostSortOrder;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.sopt.bofit.domain.post.dto.response.PostDetailResponse.PostDetailImageResponse;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_NOT_FOUND;

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

    @Transactional(readOnly = true)
    public PostDetailResponse getPostById(Long userId, Long postId) {
        Post post = getActiveById(postId);
        User writer = post.getUser();

        List<Comment> activeComments = commentRepository.findAllByPostIdAndStatus(postId, CommentStatus.ACTIVE);

        List<PostDetailImageResponse> imageUrls = postImageRepository.findByPostIdOrderBySequenceAsc(postId).stream()
                .map(image -> new PostDetailImageResponse(image.getId(), image.getImageUrl()))
                .toList();

        boolean isLike = postRepository.existsByIdAndUser(postId, writer);

        int postCommentCount = activeComments.size();

        return PostDetailResponse.builder()
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .profileImage(writer.getProfileImage())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(postCommentCount)
                .createdAt(post.getCreatedAt())
                .imageUrl(imageUrls)
                .likeCount(post.getLikeCount())
                .likedByCurrentUser(isLike)
                .category(PostCategoryResponse.from(post.getPostCategory()))
                .build();

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
