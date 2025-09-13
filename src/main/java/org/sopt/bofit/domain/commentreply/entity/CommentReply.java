package org.sopt.bofit.domain.commentreply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "idx_comment_created_at", columnList = "comment_id, created_at"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class CommentReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = CommentReplyConstant.MAX_CONTENT_LENGTH)
    private String content;

    @Enumerated(EnumType.STRING)
    private CommentReplyStatus status;

    public static CommentReply create(Comment comment, User user, String content){
        return CommentReply.builder()
            .comment(comment)
            .user(user)
            .content(content)
            .build();
    }

    @Builder
    private CommentReply(Comment comment, User user, String content) {
        this.comment = comment;
        this.user = user;
        this.content = content;
    }
}
