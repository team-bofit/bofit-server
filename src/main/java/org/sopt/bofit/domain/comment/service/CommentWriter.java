package org.sopt.bofit.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentWriter {
	private final CommentRepository commentRepository;

	public Comment create(Post post, User user, String content){
		Comment comment = Comment.create(post, user, content);

		return commentRepository.save(comment);
	}

	public Comment softDelete(Comment comment){
		return comment.softDelete();
	}

    public void increaseReplyCount(Comment comment){
        commentRepository.increaseReplyCount(comment);
    }

    public void decreaseReplyCount(Comment comment){
        commentRepository.decreaseReplyCount(comment);
    }
}
