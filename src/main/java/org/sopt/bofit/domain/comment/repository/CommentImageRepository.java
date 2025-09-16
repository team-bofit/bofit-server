package org.sopt.bofit.domain.comment.repository;

import java.util.Collection;
import java.util.List;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.entity.CommentImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentImageRepository extends JpaRepository<CommentImage, Long> {
    List<CommentImage> findAllByCommentAndStatus(Comment comment, CommentImageStatus status);

    List<CommentImage> findAllByStatusAndCommentIn(CommentImageStatus status, Collection<Comment> comments);

}
