package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.repository.PostCustomRepositoryImpl;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.dto.response.MyPostsResponse;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostCustomRepositoryImpl postCustomRepositoryImpl;

    public SliceResponse<MyPostsResponse> getMyPosts(Long userId, Pageable pageable) {
        Slice<MyPostsResponse> posts = postCustomRepositoryImpl.findMyPosts(userId, pageable);
        return SliceResponse.of(posts);
    }
}
