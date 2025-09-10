package org.sopt.bofit.domain.commentreply.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReplyWriter {

    private final CommentReplyRepository commentReplyRepository;

    public CommentReply create(Comment comment, User user, String content){
        CommentReply commentReply = CommentReply.create(comment, user, content);
        return commentReplyRepository.save(commentReply);
    }

}
