package org.sopt.bofit.domain.commentreply.repository;

import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

}
