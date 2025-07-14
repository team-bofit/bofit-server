package org.sopt.bofit.domain.comment.entity;

import static org.sopt.bofit.global.exception.constant.CommentErrorCode.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.entity.BaseEntity;
import org.sopt.bofit.global.exception.custom_exception.BadRequestException;
import org.sopt.bofit.global.exception.custom_exception.ConflictException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false, length = 300)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status;

    public static Comment create(Post post, User user, String content){
        return Comment.builder()
            .content(content)
            .post(post)
            .status(CommentStatus.ACTIVE)
            .user(user)
            .build();
    }

    @Builder
    private Comment(String content, CommentStatus status, User user, Post post) {
        this.content = content;
        this.status = status;
        this.user = user;
        this.post = post;
    }

    public void checkPost(Post post){
        if(!post.equals(this.post)){
            throw new BadRequestException(UNMATCHED_COMMENT_POST);
        }
    }

    public void checkPost(Long postId){
        if(!this.getPost().getId().equals(postId)){
            throw new BadRequestException(UNMATCHED_COMMENT_POST);
        }
    }

    public Comment softDelete(){
        if(this.status.equals(CommentStatus.INACTIVE)){
            throw new ConflictException(COMMENT_ALREADY_DELETED);
        }
        this.status = CommentStatus.INACTIVE;

        return this;
    }

}
