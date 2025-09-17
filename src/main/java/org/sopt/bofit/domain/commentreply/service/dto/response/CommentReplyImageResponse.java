package org.sopt.bofit.domain.commentreply.service.dto.response;

import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;

public record CommentReplyImageResponse(
    Long commentReplyImageId,
    String imageUrl,
    Integer sequence
) {

    public static CommentReplyImageResponse from(CommentReplyImage commentReplyImage){
        return new CommentReplyImageResponse(
            commentReplyImage.getId(),
            commentReplyImage.getImageUrl(),
            commentReplyImage.getSequence()
        );
    }

}
