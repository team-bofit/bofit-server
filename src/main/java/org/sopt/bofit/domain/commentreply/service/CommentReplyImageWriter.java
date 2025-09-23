package org.sopt.bofit.domain.commentreply.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImageStatus;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyImageRepository;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReplyImageWriter {
    private final CommentReplyImageRepository commentReplyImageRepository;

    public CommentReplyImage create(CommentReply commentReply, String imageUrl, Integer sequence){
        CommentReplyImage commentReplyImage = CommentReplyImage.create(commentReply, imageUrl, sequence);
        return commentReplyImageRepository.save(commentReplyImage);
    }

    @Transactional
    public void softDelete(Map<Long, CommentReplyImage> commentReplyImages, List<Long> deleteImageIds){
        deleteImageIds.forEach(id -> {
            commentReplyImages.get(id).softDelete();
        });
    }

    @Transactional
    public void updateAll(
        CommentReply commentReply,
        Map<Long, CommentReplyImage> currentImages,
        List<UpdateImageRequest> updatedImages
    ){
        updatedImages.forEach(image -> {
            if(image.id() == null){
                create(commentReply, image.imageUrl(), image.sequence());
            }else {
                currentImages.get(image.id()).updateSequence(image.sequence());
            }
        });
    }

    public void deleteFromCommentReply(CommentReply commentReply){
        List<CommentReplyImage> activeImages = commentReplyImageRepository.findAllByCommentReplyAndStatus(
            commentReply, CommentReplyImageStatus.ACTIVE);

        activeImages.forEach(CommentReplyImage::softDelete);
    }

}
