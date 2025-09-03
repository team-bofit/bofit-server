package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostLike;
import org.sopt.bofit.domain.post.repository.PostLikeRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeWriter {

    private final PostLikeRepository postLikeRepository;

    public PostLike create(Post post, User user){
        PostLike postLike = PostLike.create(post, user);

        postLikeRepository.save(postLike);
        return postLike;
    }

    public void delete(PostLike postLike){
        postLikeRepository.delete(postLike);
    }
}
