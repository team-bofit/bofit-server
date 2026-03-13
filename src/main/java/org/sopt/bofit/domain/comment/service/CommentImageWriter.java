package org.sopt.bofit.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.entity.CommentImageStatus;
import org.sopt.bofit.domain.comment.repository.CommentImageRepository;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.sopt.bofit.global.file.util.CloudFrontUrlCreator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentImageWriter {
    private final CommentImageRepository commentImageRepository;
    private final CloudFrontUrlCreator cloudFrontUrlCreator;

    public CommentImage create(Comment comment, String imageKey, Integer sequence){
        String cloudFrontUrl = cloudFrontUrlCreator.createCloudFrontUrl(imageKey);
        CommentImage commentImage = CommentImage.create(comment, cloudFrontUrl, sequence);
        return commentImageRepository.save(commentImage);
    }

    @Transactional
    public void updateAll(
        Comment comment,
        Map<Long, CommentImage> currentImages,
        List<UpdateImageRequest> updatedImages
    ){
        updatedImages.forEach(image -> {
                if(image.id() == null){
                    create(comment, image.imageKey(), image.sequence());
                }else {
                    currentImages.get(image.id()).updateSequence(image.sequence());
                }
            });
    }

    @Transactional
    public void delete(Map<Long, CommentImage> commentImages, List<Long> deleteImageIds){
        deleteImageIds.forEach(id -> {
            commentImages.get(id).softDelete();
        });
    }

    @Transactional
    public void deleteFromComment(Comment comment){
        List<CommentImage> activeImages = commentImageRepository.findAllByCommentAndStatus(
            comment, CommentImageStatus.ACTIVE);
        activeImages.forEach(CommentImage::softDelete);
    }

}
