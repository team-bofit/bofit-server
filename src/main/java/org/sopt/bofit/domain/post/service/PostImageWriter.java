package org.sopt.bofit.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.sopt.bofit.global.file.util.CloudFrontUrlCreator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostImageWriter {

    private final PostImageReader postImageReader;
    private final PostImageRepository postImageRepository;
    private final CloudFrontUrlCreator cloudFrontUrlCreator;

    public PostImage create(Post post, String imageKey, Integer sequence) {
        String cloudFrontUrl = cloudFrontUrlCreator.createCloudFrontUrl(imageKey);
        PostImage postImage = PostImage.create(cloudFrontUrl, post, sequence);
        return postImageRepository.save(postImage);
    }

    @Transactional
    public void updateAll(
            Post post,
            Map<Long, PostImage> currentImages,
            List<UpdateImageRequest> updatedImages
    ){
        updatedImages.forEach(image -> {
            if(image.id() == null){
                create(post, image.imageKey(), image.sequence());
            } else {
                currentImages.get(image.id()).updateSequence(image.sequence());
            }
        });
    }

    @Transactional
    public void softDelete(Map<Long, PostImage> postImages, List<Long> deleteImageIds){
        deleteImageIds.forEach(id -> {
            postImages.get(id).softDelete();
        });
    }

    public void deleteAllImagesInPost(Post post) {
        List<PostImage> allActiveImages = postImageReader.getAllActiveImages(post);
        allActiveImages.forEach(PostImage::softDelete);
    }

}
