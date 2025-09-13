package org.sopt.bofit.domain.comment.service;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.MAX_IMAGE_COUNT;
import static org.sopt.bofit.global.exception.constant.CommentErrorCode.COMMENT_UNAUTHORIZED;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.sopt.bofit.domain.comment.service.dto.request.CommentCreateCommand;
import org.sopt.bofit.domain.comment.service.dto.request.CommentUpdateCommand;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.PostReader;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.exception.constant.CommentErrorCode;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentReader commentReader;
	private final CommentWriter commentWriter;

	private final PostReader postReader;

	private final UserReader userReader;

	private final CommentImageWriter commentImageWriter;
    private final CommentImageReader commentImageReader;

	@Transactional
	public Comment createComment(Long userId, Long postId, CommentCreateCommand command){
		Post post = postReader.getActiveById(postId);
		User user = userReader.getActiveById(userId);

		Comment comment = commentWriter.create(post, user, command.content());

        IntStream.range(0, command.imageUrls().size())
                .forEach(sequence -> commentImageWriter
                    .create(comment, command.imageUrls().get(sequence), sequence + 1));

		return comment;
	}

    @Transactional
    public Comment updateComment(Long userId, Long postId, Long commentId, CommentUpdateCommand command){
        Post post = postReader.getActiveById(postId);
        User user = userReader.getActiveById(userId);
        Comment comment = commentReader.getActiveById(commentId);

        comment.getUser().checkIsWriter(userId, COMMENT_UNAUTHORIZED);
        comment.checkPost(post);

        Map<Long, CommentImage> commentImageMap = commentImageReader.getActiveImagesAsMap(comment);
        validImageCount(command.updatedImages().size());
        validImageIds(commentImageMap.keySet(), command.updatedImages().stream()
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

    /**
     * 기존 comment의 이미지가 수정 + 삭제하려는 이미지와 동일한지 검증
     */
    private void validImageIds(Set<Long> currentImageIds, List<Long> existIds, List<Long> deletedIds){
        Set<Long> expectedIds = new HashSet<>(existIds);
        expectedIds.addAll(deletedIds);

        if(!currentImageIds.equals(expectedIds)){
            throw new BadRequestException(CommentErrorCode.UNMATCHED_COMMENT_IMAGE);
        }
    }
}
