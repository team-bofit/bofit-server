package org.sopt.bofit.domain.commentreply.service.dto.response;

import java.time.LocalDateTime;

public record CommentReplyResponse(
    Long commentReplyId,

    Long writerId,
    String writerNickname,
    String profileImage,

    String content,

    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
