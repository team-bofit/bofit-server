package org.sopt.bofit.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostCreateResponse(
        @Schema(description = "게시글 ID")
        Long postId
) {
    public static PostCreateResponse from(Long postId){
        return new PostCreateResponse(postId);
    }
}
