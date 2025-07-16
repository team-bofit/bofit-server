package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.bofit.global.dto.response.CursorProvider;

import java.time.LocalDateTime;

public record PostSummaryResponse (
        @Schema(description = "게시글 ID")
        Long postId,

        @Schema(description = "작성자 ID")
        Long writerId,

        @Schema(description = "게시글 제목", example = "아니")
        String title,

        @Schema(description = "게시글 내용", example = "장정훈 그는 누구인가")
        String content,

        @Schema(description = "작성자 닉네임", example = "정훈 장")
        String writerNickname,

        @Schema(description = "작성자 프로필 사진")
        String profileImageUrl,

        @Schema(description = "댓글 수", example = "8")
        int commentCount,

        @Schema(description = "게시물 작성 시각")
        LocalDateTime createdAt

) implements CursorProvider<Long>{
        @Override
        public Long getCursor() {
                return postId;
        }
}
