package org.sopt.bofit.domain.commentreply.repository;

import java.util.List;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyImageRepository extends JpaRepository<CommentReplyImage, Long> {

    List<CommentReplyImage> findAllByCommentReplyAndStatus(CommentReply commentReply, CommentReplyImageStatus status);

}
