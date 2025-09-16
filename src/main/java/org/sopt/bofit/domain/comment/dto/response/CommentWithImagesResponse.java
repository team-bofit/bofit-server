package org.sopt.bofit.domain.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.service.dto.response.CommentImageResponse;
import org.sopt.bofit.domain.comment.service.dto.response.CommentResponse;
import org.sopt.bofit.global.dto.response.CursorProvider;

public record CommentWithImagesResponse (
    @Schema(description = "게시글 id")
    Long commentId,

    @Schema(description = "작성자 ID")
    Long writerId,

    @Schema(description = "작성자 닉네임")
    String writerNickname,

    @Schema(description = "작성자 프로필 이미지")
    String profileImage,

    @Schema(description = "댓글 내용")
    String content,

    @Schema(description = "대댓글 개수")
    Integer replyCount,

    @Schema(description = "생성 시간")
    LocalDateTime createdAt,

    @Schema(description = "수정 시간")
    LocalDateTime updatedAt,

    @Schema(description = "댓글 이미지 목록")
    List<CommentImageResponse> images
) implements CursorProvider<Long>  {

    public static CommentWithImagesResponse of(CommentResponse comments, List<CommentImage> images){
        List<CommentImageResponse> imageResponses = images.stream()
            .map(CommentImageResponse::from).toList();

        return CommentWithImagesResponse.builder()
            .commentId(comments.commentId())
            .writerId(comments.writerId())
            .writerNickname(comments.writerNickname())
            .profileImage(comments.profileImage())
            .content(comments.content())
            .replyCount(comments.replyCount())
            .createdAt(comments.createdAt())
            .updatedAt(comments.updatedAt())
            .images(imageResponses)
            .build();
    }

    @Builder
    public CommentWithImagesResponse(
        Long commentId,
        Long writerId, String writerNickname, String profileImage,
        String content, Integer replyCount,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        List<CommentImageResponse> images
    ) {
        this.commentId = commentId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.profileImage = profileImage;
        this.content = content;
        this.replyCount = replyCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.images = images;
    }

    @Override
    public Long nextCursor() {
        return commentId;
    }
}
