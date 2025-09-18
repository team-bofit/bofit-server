package org.sopt.bofit.domain.commentreply.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.service.dto.response.CommentReplyImageResponse;
import org.sopt.bofit.domain.commentreply.service.dto.response.CommentReplyResponse;
import org.sopt.bofit.global.dto.response.CursorProvider;

public record CommentReplyWithImagesResponse(
    @Schema(description = "대댓글 ID")
    Long commentReplyId,

    @Schema(description = "작성자 ID")
    Long writerId,

    @Schema(description = "작성자 닉네임")
    String writerNickname,

    @Schema(description = "작성자 프로필 이미지")
    String profileImage,

    @Schema(description = "댓글 내용")
    String content,

    @Schema(description = "생성 시간")
    LocalDateTime createdAt,

    @Schema(description = "수정 시간")
    LocalDateTime updatedAt,

    @Schema(description = "대댓글 이미지 목록")
    List<CommentReplyImageResponse> images
) implements CursorProvider<Long> {

    public static CommentReplyWithImagesResponse of(
        CommentReplyResponse commentReplyResponse,
        List<CommentReplyImage> images
    ){
        List<CommentReplyImageResponse> commentReplyImageResponses =
            images.stream().map(CommentReplyImageResponse::from).toList();

        return CommentReplyWithImagesResponse.builder()
            .commentReplyId(commentReplyResponse.commentReplyId())
            .writerId(commentReplyResponse.writerId())
            .writerNickname(commentReplyResponse.writerNickname())
            .profileImage(commentReplyResponse.profileImage())
            .content(commentReplyResponse.content())
            .createdAt(commentReplyResponse.createdAt())
            .updatedAt(commentReplyResponse.updatedAt())
            .images(commentReplyImageResponses)
            .build();
    }

    @Builder
    public CommentReplyWithImagesResponse(Long commentReplyId,
        Long writerId, String writerNickname, String profileImage,
        String content, LocalDateTime createdAt, LocalDateTime updatedAt,
        List<CommentReplyImageResponse> images
    ) {
        this.commentReplyId = commentReplyId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.profileImage = profileImage;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.images = images;
    }

    @Override
    public Long nextCursor() {
        return commentReplyId;
    }
}
