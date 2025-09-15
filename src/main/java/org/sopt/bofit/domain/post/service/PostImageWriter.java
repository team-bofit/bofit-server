package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageWriter {

    private final PostImageRepository postImageRepository;

    public PostImage create(Post post, String url, Integer sequence) {
        PostImage postImage = PostImage.create(url, post, sequence);
        return postImageRepository.save(postImage);
    }

}
