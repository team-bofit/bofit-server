package org.sopt.bofit.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.repository.CommentImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentImageWriter {
    private final CommentImageRepository commentImageRepository;

    public CommentImage create(Comment comment, String url){
        CommentImage commentImage = CommentImage.create(comment, url);
        return commentImageRepository.save(commentImage);
    }
}
