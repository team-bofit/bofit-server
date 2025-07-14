
package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.sopt.bofit.domain.post.dto.response.PostDetailResponse.*;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public SliceResponse<PostSummaryResponse> getAllPosts(Long cursorId, int size){

        Slice<PostSummaryResponse> postList = postRepository.findAllByCursorId(cursorId, size);

        return SliceResponse.of(postList);
    }

    public PostDetailResponse getPostById(Long postId) {
        Post post = findById(postId);
        User writer = post.getUser();

        List<Comment> activeComments = commentRepository.findAllByPostIdAndStatus(postId, CommentStatus.ACTIVE);

        List<CommentDetail> comments = activeComments.stream()
                .map(comment -> {
                    User commenter = comment.getUser();
                    return CommentDetail.builder()
                            .commentId(comment.getId())
                            .writerId(commenter.getId())
                            .nickname(commenter.getNickname())
                            .profileImageUrl(commenter.getProfileImage())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build();
                })
                .toList();

        return builder()
                .writerId(writer.getId())
                .nickname(writer.getNickname())
                .profileImageUrl(writer.getProfileImage())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(comments.size())
                .comments(comments)
                .build();

    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
    }
}
