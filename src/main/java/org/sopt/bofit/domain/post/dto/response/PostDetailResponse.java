package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

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

        @Schema(description = "좋아요 수")
        int likeCount,

        @Schema(description = "사용자 좋아요 여부")
        boolean likedByCurrentUser,

        @Schema(description = "이미지 url")
        List<PostDetailImageResponse> imageUrl


) {
        public record PostDetailImageResponse(
                @Schema(description = "이미지 ID")
                Long imageId,

                @Schema(description = "이미지 url")
                String imageUrl
        ){

        }
}
