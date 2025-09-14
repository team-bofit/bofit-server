package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.bofit.global.dto.response.CursorProvider;

import java.time.LocalDateTime;

public record MyPostSummaryResponse(

        @Schema(description = "게시글 ID")
        Long postId,

        @Schema(description = "제목", example = "string")
        String title,

        @Schema(description = "내용", example = "저만 삼성보험 건강보험 괜찮나요?")
        String content,

        @Schema(description = "댓글 수", example = "8")
        int commentCount,

        @Schema(description = "작성 시간")
        LocalDateTime createdAt,

        @Schema(description = "좋아요 수")
        Long likeCount,

        @Schema(description = "사용자 좋아요 여부")
        boolean isLike
) implements CursorProvider<Long> {

        @Override
        public Long getCursor() {
                return postId;
        }
}
