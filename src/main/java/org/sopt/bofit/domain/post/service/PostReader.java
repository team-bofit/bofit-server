
package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.domain.post.dto.response.PostDetailResponse.builder;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public SliceResponse<PostSummaryResponse, Long> getAllPosts(Long cursorId, int size){

        Slice<PostSummaryResponse> postList = postRepository.findAllByCursorId(cursorId, size);

        return SliceResponse.from(postList);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostById(Long postId) {
        Post post = getActiveById(postId);
        User writer = post.getUser();

        List<Comment> activeComments = commentRepository.findAllByPostIdAndStatus(postId, CommentStatus.ACTIVE);

        long postCommentCount = activeComments.size();

        return builder()
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .profileImage(writer.getProfileImage())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(postCommentCount)
                .createdAt(post.getCreatedAt())
                .build();

    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
    }

    public Post getActiveById(Long postId) {
        return postRepository.findByIdAndStatus(postId, PostStatus.ACTIVE).orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
    }
}
