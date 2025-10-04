package org.sopt.bofit.domain.post.entity;

import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bofit.domain.post.entity.constant.PostImageStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false, length = MAX_IMAGE_URL_LENGTH)
    private String imageUrl;

    private Integer sequence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    PostImageStatus status = PostImageStatus.ACTIVE;

    public static PostImage create(String imageUrl, Post post, int sequence){
        return PostImage.builder()
                .imageUrl(imageUrl)
                .post(post)
                .sequence(sequence)
                .build();
    }

    public void updateImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void updateSequence(int sequence){
        this.sequence = sequence;
    }

    public void softDelete(){
        this.status = PostImageStatus.INACTIVE;
        this.sequence = null;
    }
}
