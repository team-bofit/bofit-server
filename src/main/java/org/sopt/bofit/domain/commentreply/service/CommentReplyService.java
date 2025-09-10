package org.sopt.bofit.domain.commentreply.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.service.CommentReader;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentReplyService {

    private CommentReplyWriter commentReplyWriter;
    private CommentReplyImageWriter commentReplyImageWriter;
    private CommentReader commentReader;
    private UserReader userReader;

    @Transactional
    public CommentReply create(Long userId, Long postId, Long commentId, String content, Optional<String> imageUrl){
        Comment comment = commentReader.findById(commentId);
        User user = userReader.findById(userId);

        comment.checkPost(postId);

        CommentReply commentReply = commentReplyWriter.create(comment, user, content);
        imageUrl.ifPresent((url) -> commentReplyImageWriter.create(commentReply, url));
        return commentReply;
    }

}
