package org.sopt.bofit.domain.comment.repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.entity.CommentImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentImageRepository extends JpaRepository<CommentImage, Long> {
    List<CommentImage> findAllByCommentAndStatus(Comment comment, CommentImageStatus status);

    default Map<Long, CommentImage> findAllByCommentAndStatusAsMap(Comment comment, CommentImageStatus status){
        return findAllByCommentAndStatus(comment, status).stream()
            .collect(Collectors.toMap(CommentImage::getId, Function.identity()));
    }
}
