package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.file.dto.request.NewImageRequest;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostReader postReader;

    private final PostWriter postWriter;

    public PostCreateResponse createPost(Long userId, String title, String content, String category, List<String> imageUrls) {
        return postWriter.createPost(userId, title, content, category, imageUrls);
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, String title, String content,
                                          List<NewImageRequest> newImages, List<UpdateImageRequest> updateImages,
                                          List<Long> deleteImageIds) {
        return postWriter.updatePost(userId, postId, title, content, newImages, updateImages, deleteImageIds);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        postWriter.deletePost(userId, postId);
    }

    public SliceResponse<PostSummaryResponse, Long> getAllPosts(Long cursorId, int size){
        return postReader.getAllPosts(cursorId, size);
    }

    public PostDetailResponse getPostDetail(Long postId){
        return postReader.getPostById(postId);
    }

}
