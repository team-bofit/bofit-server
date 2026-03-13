package org.sopt.bofit.domain.commentreply.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImageStatus;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyImageRepository;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.sopt.bofit.global.file.util.CloudFrontUrlCreator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentReplyImageWriter {
    private final CommentReplyImageRepository commentReplyImageRepository;
    private final CloudFrontUrlCreator cloudFrontUrlCreator;

    public CommentReplyImage create(CommentReply commentReply, String imageKey, Integer sequence){
        String cloudFrontUrl = cloudFrontUrlCreator.createCloudFrontUrl(imageKey);
        CommentReplyImage commentReplyImage = CommentReplyImage.create(commentReply, cloudFrontUrl, sequence);
        return commentReplyImageRepository.save(commentReplyImage);
    }

    @Transactional
    public void delete(Map<Long, CommentReplyImage> commentReplyImages, List<Long> deleteImageIds){
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
                create(commentReply, image.imageKey(), image.sequence());
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
