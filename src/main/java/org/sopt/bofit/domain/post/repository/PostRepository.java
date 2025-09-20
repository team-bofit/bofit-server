package org.sopt.bofit.domain.post.repository;

import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.user.entity.User;
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

    boolean existsByIdAndUser(Long postId, User user);

    @Modifying
    @Query("""
        UPDATE Post p
        SET p.writerNickname = :nickname
        WHERE p.user.id = :userId"""

    )
    void updateWriterNicknameByUserId(@Param("userId") Long userId, @Param("nickname") String nickname);


}
