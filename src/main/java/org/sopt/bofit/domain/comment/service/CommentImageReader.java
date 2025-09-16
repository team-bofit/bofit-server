package org.sopt.bofit.domain.comment.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.entity.CommentImageStatus;
import org.sopt.bofit.domain.comment.repository.CommentImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentImageReader {
    private final CommentImageRepository commentImageRepository;

    public Map<Long, CommentImage> getActiveImagesAsMap(Comment comment){
        return commentImageRepository.findAllByCommentAndStatus(comment,CommentImageStatus.ACTIVE).stream()
            .collect(Collectors.toMap(CommentImage::getId, Function.identity()));
    }

    public Map<Long, List<CommentImage>> groupActiveImagesByCommentIds(List<Long> commentIds){
        return commentImageRepository.findAllByStatusAndCommentIdIn(CommentImageStatus.ACTIVE, commentIds).stream()
            .sorted(CommentImage::compareTo)
            .collect(Collectors.groupingBy(commentImage -> commentImage.getComment().getId()));
    }

}
