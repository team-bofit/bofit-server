package org.sopt.bofit.domain.post.service.dto.response;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostCategory;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;

@Getter
public class TrendingPostDto {

    private Long postId;

    private Long writerId;
    private String writerNickname;

    private String title;
    private String content;
    private PostStatus status;
    private PostCategory postCategory;

    private int likeCount;
    private int commentCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private TrendingPostDto(Long postId,
        Long writerId, String writerNickname,
        String title, String content, PostStatus status, PostCategory postCategory,
        int likeCount, int commentCount,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.title = title;
        this.content = content;
        this.status = status;
        this.postCategory = postCategory;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TrendingPostDto from(Post post){
        return TrendingPostDto.builder()
            .postId(post.getId())
            .writerId(post.getUser().getId())
            .writerNickname(post.getWriterNickname())
            .title(post.getTitle())
            .content(post.getContent())
            .status(post.getStatus())
            .postCategory(post.getPostCategory())
            .likeCount(post.getLikeCount())
            .commentCount(post.getCommentCount())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .build();
    }

    public void increaseLikeCount(){
        this.likeCount++;
    }

    public void decreaseLikeCount(){
        this.likeCount--;
    }

    public void increaseCommentCount(){
        this.commentCount++;
    }

    public void decreaseCommentCount(){
        this.commentCount--;
    }
}
