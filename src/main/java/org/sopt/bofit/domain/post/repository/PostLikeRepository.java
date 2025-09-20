package org.sopt.bofit.domain.post.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostLike;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostAndUser(Post post, User user);

    Optional<PostLike> findByPostAndUser(Post post, User user);

    @Query("""
        select p.id
        from PostLike pl
        join pl.post p
        where pl.user = :user and p in :posts
    """)
    Set<Long> findLikedPostIdsByUserAndPosts(@Param("user") User user, @Param("posts") List<Post> posts);
}
