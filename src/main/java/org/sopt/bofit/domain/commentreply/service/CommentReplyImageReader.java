package org.sopt.bofit.domain.commentreply.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImageStatus;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReplyImageReader {

    private final CommentReplyImageRepository commentReplyImageRepository;

    public Map<Long, CommentReplyImage> getActiveImagesAsMap(CommentReply commentReply){
        return commentReplyImageRepository.findAllByCommentReplyAndStatus(commentReply, CommentReplyImageStatus.ACTIVE).stream()
            .collect(Collectors.toMap(CommentReplyImage::getId, Function.identity()));
    }

    public Map<Long, List<CommentReplyImage>> groupActiveImagesByReplyIds(Collection<Long> replyIds){
        return commentReplyImageRepository.findAllByStatusAndCommentReplyIdIn(CommentReplyImageStatus.ACTIVE, replyIds).stream()
            .sorted(CommentReplyImage::compareTo)
            .collect(Collectors.groupingBy(image-> image.getCommentReply().getId()));
    }
}
