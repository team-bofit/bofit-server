package org.sopt.bofit.domain.commentreply.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReplyImageWriter {
    private final CommentReplyImageRepository commentReplyImageRepository;

    public CommentReplyImage create(CommentReply commentReply, String imageUrl){
        CommentReplyImage commentReplyImage = CommentReplyImage.create(commentReply, imageUrl);
        return commentReplyImageRepository.save(commentReplyImage);
    }

}
