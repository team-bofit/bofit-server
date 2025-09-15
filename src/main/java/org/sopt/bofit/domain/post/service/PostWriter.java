package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class PostWriter {

    private final UserReader userReader;

    private final PostReader postReader;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public Post createPost(Long userId, String title, String content, String category) {
        User user = userReader.getActiveById(userId);
        Post post = Post.create(user, title, content, category, user.getNickname());

        return postRepository.save(post);
    }


    @Transactional
    public void deletePost(Long userId, Long postId) {
        User user = userReader.getActiveById(userId);
        Post post = postReader.getActiveById(postId);
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

    @Transactional
    public void increaseCommentCount(Post post){
        postRepository.increaseCommentCount(post);
    }

    @Transactional
    public void decreaseCommentCount(Post post){
        postRepository.decreaseCommentCount(post);
    }
}
