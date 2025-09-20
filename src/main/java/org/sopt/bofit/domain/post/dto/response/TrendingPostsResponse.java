package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import org.sopt.bofit.domain.post.service.dto.response.TrendingPostDto;

@Builder
public record TrendingPostsResponse (
    @Schema(description = "게시글 ID")
    Long postId,

    @Schema(description = "작성자 ID")
    Long writerId,

    @Schema(description = "닉네임", example = "정훈 장")
    String writerNickname,

    @Schema(description = "게시물 제목")
    String title,

    @Schema(description = "게시물 내용")
    String content,

    @Schema(description = "댓글 수 ", example = "8")
    long commentCount,

    @Schema(description = "생성 시간")
    LocalDateTime createdAt,

    @Schema(description = "좋아요 수")
    Long likeCount,

    @Schema(description = "사용자 좋아요 여부")
    boolean likedByCurrentUser,

    @Schema(description = "게시글 카테고리")
    PostCategoryResponse category
){

    public static TrendingPostsResponse of(TrendingPostDto post, boolean isLiked){
        return TrendingPostsResponse.builder()
            .postId(post.getPostId())
            .writerId(post.getWriterId())
            .writerNickname(post.getWriterNickname())
            .title(post.getTitle())
            .content(post.getContent())
            .commentCount(post.getCommentCount())
            .likeCount(post.getLikeCount())
            .createdAt(post.getCreatedAt())
            .likedByCurrentUser(isLiked)
            .category(PostCategoryResponse.from(post.getPostCategory()))
            .build();
    }

}
