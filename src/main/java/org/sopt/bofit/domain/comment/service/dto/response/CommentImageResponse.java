package org.sopt.bofit.domain.comment.service.dto.response;

import org.sopt.bofit.domain.comment.entity.CommentImage;

public record CommentImageResponse(
    Long imageId,
    String imageUrl,
    Integer sequence
) {

    public static CommentImageResponse from(CommentImage commentImage) {
        return new CommentImageResponse(commentImage.getId(),  commentImage.getImageUrl(), commentImage.getSequence());
    }

}
