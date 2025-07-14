package org.sopt.bofit.domain.comment.dto.response;


import java.time.LocalDateTime;

public record CommentResponse (
	Long commentId,
	String nickname,
	String profileImage,
	String content,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
){
}
