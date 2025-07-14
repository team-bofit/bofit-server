package org.sopt.bofit.domain.comment.dto.response;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentResponse (
	@Schema(description = "게시글 id")
	Long commentId,
	@Schema(description = "작성자 닉네임")
	String nickname,
	@Schema(description = "작성자 프로필 이미지")
	String profileImage,
	@Schema(description = "댓글 내용")
	String content,
	@Schema(description = "생성 시간")
	LocalDateTime createdAt,
	@Schema(description = "수정 시간")
	LocalDateTime updatedAt
){
}
