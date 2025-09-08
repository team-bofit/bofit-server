package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class PostWriter {

    private final UserReader userReader;

    private final PostReader postReader;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostImageRepository postImageRepository;


    @Transactional
    public PostCreateResponse createPost(Long userId, String title, String content, List<String> imageUrls) {
        User user = userReader.findById(userId);
        Post newPost = Post.create(user, title, content);
        postRepository.save(newPost);

        int sequence = 1;
        List<PostImage> postImages = new ArrayList<>();

        if(imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                postImages.add(PostImage.create(imageUrl, newPost, sequence++));
            }
        }

        postImageRepository.saveAll(postImages);

        return PostCreateResponse.from(newPost.getId());
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, String title, String content) {
        User user = userReader.findById(userId);
        Post post = postReader.findById(postId);

        post.getUser().checkIsWriter(userId, POST_UNAUTHORIZED);
        post.updatePost(title, content);

        return PostCreateResponse.from(post.getId());
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        User user = userReader.findById(userId);
        Post post = postReader.findById(postId);
        post.getUser().checkIsWriter(userId, POST_UNAUTHORIZED);

        postRepository.deletePostByPostId(postId);

        commentRepository.findAllByPostIdAndStatus(postId, CommentStatus.ACTIVE).forEach(Comment::softDelete);

    }
    @Transactional
    public void increaseLikeCount(Post post){
        postRepository.increaseLikeCount(post);
    }

    @Transactional
    public void decreaseLikeCount(Post post){
        postRepository.decreaseLikeCount(post);
    }
}
