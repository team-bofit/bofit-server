package org.sopt.bofit.domain.comment.entity;

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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(length = ImageConstant.MAX_IMAGE_URL_LENGTH)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CommentImageStatus status;

    private Integer sequence;

    public static CommentImage create(Comment comment, String imageUrl, Integer sequence){
        return CommentImage.builder()
            .comment(comment)
            .imageUrl(imageUrl)
            .sequence(sequence)
            .status(CommentImageStatus.ACTIVE)
            .build();
    }

    @Builder
    private CommentImage(Comment comment, String imageUrl, CommentImageStatus status, Integer sequence) {
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.status = status;
        this.sequence = sequence;
    }

    public void softDelete(){
        this.status = CommentImageStatus.INACTIVE;
        this.sequence = null;
    }

    public void updateSequence(Integer newSequence){
        this.sequence = newSequence;
    }

}