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

    public void delete(Post post) {
        post.softDelete();

        commentRepository.findAllByPostIdAndStatus(post.getId(), CommentStatus.ACTIVE).forEach(Comment::softDelete);
    }

    @Transactional
    public void increaseLikeCount(Post post){
        postRepository.increaseLikeCountAndTrendScore(post);
    }

    @Transactional
    public void decreaseLikeCount(Post post){
        postRepository.decreaseLikeCountAndTrendScore(post);
    }

    @Transactional
    public void increaseCommentCount(Post post){
        postRepository.increaseCommentCountAndTrendScore(post);
    }

    @Transactional
    public void decreaseCommentCount(Post post){
        postRepository.decreaseCommentCountAndTrendScore(post);
    }

    @Transactional
    public void increaseTrendScore(Post post){
        postRepository.increaseTrendScore(post);
    }

    @Transactional
    public void decreaseTrendScore(Post post){
        postRepository.decreaseTrendScore(post);
    }
}
