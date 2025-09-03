package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.entity.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PostJpaRepository, PostCustomRepository{

    @Modifying
    @Query("""
		update Post p
		set p.likeCount = p.likeCount + 1
		where p = :requestPost
		""")
    void increaseLikeCount(@Param("requestPost") Post post);

    @Query("""
        UPDATE Post p
        SET p.likeCount = p.likeCount - 1
        WHERE p = :requestPost
        """)
    void decreaseLikeCount(@Param("requestPost") Post post);
}
