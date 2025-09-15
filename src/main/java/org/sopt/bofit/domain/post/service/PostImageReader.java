package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostImageReader {

    private final PostImageRepository postImageRepository;

    public Map<Long, PostImage> getActiveImageAsMap(Post post){
        return postImageRepository.findAllByPostAndStatus(post, PostStatus.ACTIVE).stream()
                .collect(Collectors.toMap(PostImage::getId, Function.identity()));
    }

}
