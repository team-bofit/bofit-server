package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostResponse(
        @Schema(description = "게시글 ID")
        Long postId
) {
    public static PostResponse from(Long postId){
        return new PostResponse(postId);
    }
}
