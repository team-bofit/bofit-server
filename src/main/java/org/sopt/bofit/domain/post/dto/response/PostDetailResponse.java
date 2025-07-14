package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostDetailResponse(
        @Schema(description = "작성자 ID")
        Long writerId,

        @Schema(description = "닉네임", example = "정훈 장")
        String nickname,

        @Schema(description = "프로필 사진 url")
        String profileImage,

        @Schema(description = "게시물 제목")
        String title,

        @Schema(description = "게시물 내용")
        String content,

        @Schema(description = "댓글 수 ", example = "8")
        long commentCount
) {

}
