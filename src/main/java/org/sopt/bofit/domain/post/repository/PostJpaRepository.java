package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndStatus(Long postId, PostStatus postStatus);

    boolean existsByIdAndUser(Long postId, User user);

    @Modifying
    @Query("""
		update Post p
		set p.likeCount = p.likeCount + 1
		where p = :requestPost
		""")
    void increaseLikeCount(@Param("requestPost") Post post);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.likeCount = p.likeCount - 1
        WHERE p = :requestPost
        """)
    void decreaseLikeCount(@Param("requestPost") Post post);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.writerNickname = :writerNickname
        WHERE p.user.id = :userId""")
    void updateWriterNicknameByUserId(@Param("writerNickname") String writerNickname,
        @Param("userId") Long userId
    );

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.commentCount = p.commentCount + 1
        WHERE p = :requestPost
        """
    )
    void increaseCommentCount(@Param("requestPost") Post post);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.commentCount = p.commentCount - 1
        WHERE p = :requestPost
        """
    )
    void decreaseCommentCount(@Param("requestPost") Post post);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.writerNickname = :nickname
        WHERE p.user.id = :userId"""

    )
    void updateWriterNicknameByUserId(@Param("userId") Long userId, @Param("nickname") String nickname);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.trendScore = p.trendScore + 1
        WHERE p = :requestPost
        """
    )
    void increaseTrendScore(@Param("requestPost") Post post);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.trendScore = p.trendScore - 1
        WHERE p = :requestPost
        """
    )
    void decreaseTrendScore(@Param("requestPost") Post post);

}
