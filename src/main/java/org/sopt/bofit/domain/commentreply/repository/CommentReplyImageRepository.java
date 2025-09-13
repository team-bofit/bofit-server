package org.sopt.bofit.domain.commentreply.repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyImageRepository extends JpaRepository<CommentReplyImage, Long> {

    List<CommentReplyImage> findAllByCommentReplyAndStatus(CommentReply commentReply, CommentReplyImageStatus status);

    default Map<Long, CommentReplyImage> findAllByCommentReplyAndStatusAsMap(
        CommentReply commentReply, CommentReplyImageStatus status
    ){
        return findAllByCommentReplyAndStatus(commentReply, status).stream()
            .collect(Collectors.toMap(CommentReplyImage::getId, Function.identity()));
    }
}
