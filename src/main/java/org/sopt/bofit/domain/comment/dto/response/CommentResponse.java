package org.sopt.bofit.domain.comment.dto.response;

import java.time.LocalDateTime;

public record CommentResponse (
	Long commentId,

	Long writerId,
	String writerNickname,
	String profileImage,

	String content,
    Integer replyCount,

	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

}
