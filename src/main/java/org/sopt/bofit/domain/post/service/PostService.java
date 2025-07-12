package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostReader postReader;

    private final PostWriter postWriter;

    public PostCreateResponse createPost(Long userId, String title, String content) {
        return postWriter.createPost(userId, title, content);
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, String title, String content) {
        return postWriter.updatePost(userId, postId, title, content);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        postWriter.deletePost(userId, postId);
    }

    public SliceResponse<PostSummaryResponse> getAllPosts(Long cursorId, int size){
        return postReader.getAllPosts(cursorId, size);
    }


}
