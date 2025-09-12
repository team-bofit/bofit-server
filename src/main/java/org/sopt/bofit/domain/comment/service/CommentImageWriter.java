package org.sopt.bofit.domain.comment.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.repository.CommentImageRepository;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentImageWriter {
    private final CommentImageRepository commentImageRepository;

    public CommentImage create(Comment comment, String url, Integer sequence){
        CommentImage commentImage = CommentImage.create(comment, url);
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
                    create(comment, image.imageUrl(), image.sequence());
                }else {
                    currentImages.get(image.id()).updateSequence(image.sequence());
                }
            });
    }

    @Transactional
    public void softDelete(Map<Long, CommentImage> commentImages, List<Long> deleteImageIds){
        deleteImageIds.forEach(id -> {
            commentImages.get(id).softDelete();
        });
    }


}
