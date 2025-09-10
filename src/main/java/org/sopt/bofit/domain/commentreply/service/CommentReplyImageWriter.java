package org.sopt.bofit.domain.commentreply.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyImageRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReplyImageWriter {
    private final CommentReplyImageRepository commentReplyImageRepository;

    public CommentReplyImage create(CommentReply commentReply, User user, String imageUrl){
        CommentReplyImage commentReplyImage = CommentReplyImage.create(commentReply, user, imageUrl);
        return commentReplyImageRepository.save(commentReplyImage);
    }

}
