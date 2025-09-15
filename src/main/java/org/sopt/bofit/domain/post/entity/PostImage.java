package org.sopt.bofit.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.bofit.domain.post.entity.constant.PostImageStatus;

import static org.sopt.bofit.global.file.constant.ImageConstant.MAX_IMAGE_URL_LENGTH;

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

    @Column(nullable = false)
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
