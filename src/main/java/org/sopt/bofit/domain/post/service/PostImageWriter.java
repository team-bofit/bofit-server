package org.sopt.bofit.domain.post.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageWriter {

    private final PostImageReader postImageReader;
    private final PostImageRepository postImageRepository;

    public PostImage create(Post post, String url, Integer sequence) {
        PostImage postImage = PostImage.create(url, post, sequence);
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
                create(post, image.imageUrl(), image.sequence());
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
