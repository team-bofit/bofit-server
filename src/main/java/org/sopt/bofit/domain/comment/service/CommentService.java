package org.sopt.bofit.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.service.dto.request.CommentCreateCommand;
import org.sopt.bofit.domain.comment.service.dto.request.CommentUpdateCommand;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.PostReader;
import org.sopt.bofit.domain.post.service.PostWriter;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.exception.constant.CommentErrorCode;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.sopt.bofit.global.file.util.ImageValidator;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.MAX_IMAGE_COUNT;
import static org.sopt.bofit.global.exception.constant.CommentErrorCode.COMMENT_UNAUTHORIZED;


@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentReader commentReader;

	private final CommentWriter commentWriter;

	private final PostReader postReader;

	private final UserReader userReader;

	private final CommentImageWriter commentImageWriter;
    private final CommentImageReader commentImageReader;

	private final PostWriter postWriter;


	@Transactional
	public Comment createComment(Long userId, Long postId, CommentCreateCommand command){
		Post post = postReader.getActiveById(postId);
		User user = userReader.getActiveById(userId);

		Comment comment = commentWriter.create(post, user, command.content());

        IntStream.range(0, command.imageUrls().size())
                .forEach(sequence -> commentImageWriter
                    .create(comment, command.imageUrls().get(sequence), sequence + 1));

		postWriter.increaseCommentCount(post);

		return comment;
	}

    @Transactional
    public Comment updateComment(Long userId, Long postId, Long commentId, CommentUpdateCommand command){
        Post post = postReader.getActiveById(postId);
        User user = userReader.getActiveById(userId);
        Comment comment = commentReader.getActiveById(commentId);

        validRelation(user, post, comment);

        Map<Long, CommentImage> commentImageMap = commentImageReader.getActiveImagesAsMap(comment);
        validImageCount(command.updatedImages().size());

        ImageValidator.validImageIds(commentImageMap.keySet(), command.updatedImages().stream()
                        .filter(image -> image.id() != null).map(UpdateImageRequest::id).toList(),
                command.deleteImageIds());

        commentImageWriter.softDelete(commentImageMap, command.deleteImageIds());
        commentImageWriter.updateAll(comment, commentImageMap, command.updatedImages());
        command.content().ifPresent(comment::updateContent);

        return comment;
    }

	@Transactional
	public void deleteComment(Long userId, Long postId, Long commentId) {
		Comment comment = commentReader.getActiveById(commentId);
		Post post = postReader.getActiveById(postId);

		comment.getUser().checkIsWriter(userId, COMMENT_UNAUTHORIZED);
		comment.checkPost(post);

		postWriter.decreaseCommentCount(post);

		commentWriter.softDelete(comment);
	}

	public SliceResponse<CommentResponse, Long> findAllByPostIdAndCursor(Long postId, Long userId, Optional<Long> cursor, int size) {
		Post post = postReader.getActiveById(postId);

		Slice<CommentResponse> commentsByCursorId = commentReader.findCommentsByCursorId(postId, cursor, size);

		return SliceResponse.from(commentsByCursorId);
	}

    private void validImageCount(int existImageCount){
        if(MAX_IMAGE_COUNT < existImageCount){
            throw new BadRequestException(CommentErrorCode.COMMENT_IMAGE_EXCEED);
        }
    }


    private void validRelation(User user, Post post, Comment comment){
        comment.getUser().checkIsWriter(user, COMMENT_UNAUTHORIZED);
        comment.checkPost(post);
    }
}
