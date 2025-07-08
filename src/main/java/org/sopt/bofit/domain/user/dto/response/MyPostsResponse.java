package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MyPostsResponse<T>(

        @Schema(description = "제목", example = "string")
        String title,

        @Schema(description = "내용", example = "저만 삼성보험 건강보험 괜찮나요?")
        String content,

        @Schema(description = "댓글 수", example = "8")
        int commentCount,

        @Schema(description = "작성 시간")
        LocalDateTime createdAt
){

    public MyPostsResponse<T> of(String title, String content, int commentCount, LocalDateTime createdAt){
        return new MyPostsResponse<>(title, content, commentCount, createdAt);
    }
}
