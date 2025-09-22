package org.sopt.bofit.domain.post.entity;

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
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sopt.bofit.domain.post.entity.constant.PostCategory;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.entity.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    @Column(nullable = false)
    private String writerNickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PostStatus status = PostStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private int likeCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostCategory postCategory;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private int commentCount;

    public static Post create(User user, String title, String content, String category, String writerNickname) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .likeCount(0)
                .commentCount(0)
                .postCategory(Enum.valueOf(PostCategory.class, category))
                .status(PostStatus.ACTIVE)
                .writerNickname(writerNickname)
                .build();
    }

    public void updatePost(String title, String content, String category){
        this.title = title;
        this.content = content;
        this.postCategory = Enum.valueOf(PostCategory.class, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Post that)) {
            return false;
        }
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
