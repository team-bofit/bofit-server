package org.sopt.bofit.domain.commentreply.repository;

import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyImageRepository extends JpaRepository<CommentReplyImage, Long> {

}
