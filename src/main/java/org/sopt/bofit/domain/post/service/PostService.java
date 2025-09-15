package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.dto.request.PostCreateCommand;
import org.sopt.bofit.domain.post.service.dto.request.PostUpdateCommand;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostReader postReader;

    private final PostWriter postWriter;

    private final UserReader userReader;

    private final PostImageWriter postImageWriter;

    @Transactional
    public PostCreateResponse createPost(Long userId, PostCreateCommand command) {
        User user = userReader.getActiveById(userId);

        Post post = postWriter.createPost(userId, command.title(), command.content(), command.category());

        IntStream.range(0, command.imageUrls().size())
                .forEach(sequence -> postImageWriter
                        .create(post, command.imageUrls().get(sequence), sequence + 1));

        return PostCreateResponse.from(post.getId());
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, PostUpdateCommand command) {
        return postWriter.updatePost(userId, postId, command);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        postWriter.deletePost(userId, postId);
    }

    public SliceResponse<PostSummaryResponse, Long> getAllPosts(Long userId, Long cursorId, int size){
        return postReader.getAllPosts(userId, cursorId, size);
    }

    public PostDetailResponse getPostDetail(Long userId, Long postId){
        return postReader.getPostById(userId, postId);
    }

    public SliceResponse<PostSummaryResponse, Long> searchPosts(Long userId, String keyword, Long cursorId, int size){
        return postReader.findPostsByKeywordAndCursorId(userId, keyword, cursorId, size);
    }

}
