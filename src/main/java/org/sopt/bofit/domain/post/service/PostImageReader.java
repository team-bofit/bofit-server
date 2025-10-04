package org.sopt.bofit.domain.post.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.entity.constant.PostImageStatus;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageReader {

    private final PostImageRepository postImageRepository;

    public Map<Long, PostImage> getActiveImageAsMap(Post post){
        return postImageRepository.findAllByPostAndStatus(post, PostImageStatus.ACTIVE).stream()
                .collect(Collectors.toMap(PostImage::getId, Function.identity()));
    }

    public List<PostImage> getAllActiveImages(Post post) {
        return postImageRepository.findAllByPostAndStatus(post, PostImageStatus.ACTIVE);
    }
}
