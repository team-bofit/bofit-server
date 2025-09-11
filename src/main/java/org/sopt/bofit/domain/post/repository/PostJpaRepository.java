package org.sopt.bofit.domain.post.repository;

import java.util.Optional;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndStatus(Long postId, PostStatus postStatus);
}
