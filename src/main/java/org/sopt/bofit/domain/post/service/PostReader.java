package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.repository.PostCustomRepositoryImpl;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;

    private final PostCustomRepositoryImpl postCustomRepositoryImpl;

    public SliceResponse<PostSummaryResponse> getAllPosts(Long cursorId, int size){

        Slice<PostSummaryResponse> postList = postCustomRepositoryImpl.findAllByCursorId(cursorId, size);

        return SliceResponse.of(postList);
    }
}
