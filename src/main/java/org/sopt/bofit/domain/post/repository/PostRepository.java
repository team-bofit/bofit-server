package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
