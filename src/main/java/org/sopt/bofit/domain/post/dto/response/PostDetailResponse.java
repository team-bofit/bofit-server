package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.user.entity.User;

@Builder
public record PostDetailResponse(
        @Schema(description = "작성자 ID")
        Long writerId,

        @Schema(description = "닉네임", example = "정훈 장")
        String writerNickname,

        @Schema(description = "프로필 사진 url")
        String profileImage,

        @Schema(description = "게시물 제목")
        String title,

        @Schema(description = "게시물 내용")
        String content,

        @Schema(description = "댓글 수 ", example = "8")
        int commentCount,

        @Schema(description = "생성 시간")
        LocalDateTime createdAt,

        @Schema(description = "수정 시간")
        LocalDateTime updatedAt,

        @Schema(description = "좋아요 수")
        int likeCount,

        @Schema(description = "사용자 좋아요 여부")
        boolean likedByCurrentUser,

        @Schema(description = "게시물 카테고리")
        PostCategoryResponse category,

        @Schema(description = "이미지 url")
        List<PostDetailImageResponse> imageUrl


) {
        public record PostDetailImageResponse(
                @Schema(description = "이미지 ID")
                Long imageId,

                @Schema(description = "이미지 url")
                String imageUrl
        ){
            public static PostDetailImageResponse from(PostImage postImage){
                return new PostDetailImageResponse(postImage.getId(), postImage.getImageUrl());
            }
        }

        public static PostDetailResponse of(
            Post post,
            User writer,
            boolean isLiked,
            List<PostImage> postImages
        ){
            List<PostDetailImageResponse> imageUrls = postImages.stream().map(PostDetailImageResponse::from).toList();

            return PostDetailResponse.builder()
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .profileImage(writer.getProfileImage())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .imageUrl(imageUrls)
                .likeCount(post.getLikeCount())
                .likedByCurrentUser(isLiked)
                .category(PostCategoryResponse.from(post.getPostCategory()))
                .build();
        }
}
