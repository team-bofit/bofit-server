package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.entity.constant.PostImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostIdOrderBySequenceAsc(Long postId);

    @Modifying
    @Query("delete from PostImage pi where pi.post.id = :postId and pi.id in :ids")
    void deleteAllByPostIdAndIdIn(@Param("postId") Long postId, @Param("ids") List<Long> ids);

    boolean existsByIdAndPostId(Long id, Long postId);

    List<PostImage> findAllByPostAndStatus(Post post, PostImageStatus status);
}
