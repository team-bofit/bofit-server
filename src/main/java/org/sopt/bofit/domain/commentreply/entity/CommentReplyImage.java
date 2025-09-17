package org.sopt.bofit.domain.commentreply.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bofit.global.entity.BaseEntity;
import org.sopt.bofit.global.file.constant.ImageConstant;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReplyImage extends BaseEntity implements Comparable<CommentReplyImage> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_reply_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_reply_id")
    private CommentReply commentReply;

    @Column(length = ImageConstant.MAX_IMAGE_URL_LENGTH)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CommentReplyImageStatus status;

    private Integer sequence;

    public static CommentReplyImage create(CommentReply commentReply, String imageUrl, Integer sequence) {
        return CommentReplyImage.builder()
            .commentReply(commentReply)
            .imageUrl(imageUrl)
            .sequence(sequence)
            .build();
    }

    @Builder
    private CommentReplyImage(CommentReply commentReply, String imageUrl, Integer sequence, CommentReplyImageStatus status) {
        this.commentReply = commentReply;
        this.imageUrl = imageUrl;
        this.sequence = sequence;
        this.status = CommentReplyImageStatus.ACTIVE;
    }

    public void softDelete(){
        this.sequence = null;
        this.status = CommentReplyImageStatus.INACTIVE;
    }

    public void updateSequence(Integer sequence){
        this.sequence = sequence;
    }

    @Override
    public int compareTo(CommentReplyImage commentReplyImage) {
        return Integer.compare(this.sequence, commentReplyImage.sequence);
    }
}
