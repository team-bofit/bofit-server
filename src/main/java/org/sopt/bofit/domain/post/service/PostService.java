package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.service.dto.request.PostUpdateCommand;
import org.sopt.bofit.global.dto.response.SliceResponse;
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
    public PostCreateResponse updatePost (Long userId, Long postId, PostUpdateCommand command) {
        return postWriter.updatePost(userId, postId, command);
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

    public SliceResponse<PostSummaryResponse, Long> searchPosts(String keyword, Long cursorId, int size){
        return postReader.findPostsByKeywordAndCursorId(keyword, cursorId, size);
    }

}
