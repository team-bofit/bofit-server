package org.sopt.bofit.domain.comment.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.bofit.global.dto.response.CursorProvider;

import java.time.LocalDateTime;

public record CommentResponse (
	@Schema(description = "게시글 id")
	Long commentId,

	@Schema(description = "작성자 ID")
	Long writerId,

	@Schema(description = "작성자 닉네임")
	String wrtierNickname,

	@Schema(description = "작성자 프로필 이미지")
	String profileImage,

	@Schema(description = "댓글 내용")
	String content,

	@Schema(description = "생성 시간")
	LocalDateTime createdAt,

	@Schema(description = "수정 시간")
	LocalDateTime updatedAt
) implements CursorProvider<Long>
{
	@Override
	public Long getCursor() {
		return commentId;
	}
}
