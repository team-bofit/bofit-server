package org.sopt.bofit.domain.commentreply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.sopt.bofit.global.entity.BaseEntity;
import org.sopt.bofit.global.file.constant.ImageConstant;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReplyImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CommentReply commentReply;

    @Column(length = ImageConstant.MAX_IMAGE_URL_LENGTH)
    String imageUrl;

    public static CommentReplyImage create(CommentReply commentReply, String imageUrl){
        return CommentReplyImage.builder()
            .commentReply(commentReply)
            .imageUrl(imageUrl)
            .build();
    }

    @Builder
    private CommentReplyImage(CommentReply commentReply, String imageUrl) {
        this.commentReply = commentReply;
        this.imageUrl = imageUrl;
    }
}
