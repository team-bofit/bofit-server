package org.sopt.bofit.domain.commentreply.service;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.service.CommentReader;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyImage;
import org.sopt.bofit.domain.commentreply.service.dto.request.CommentReplyCreateCommand;
import org.sopt.bofit.domain.commentreply.service.dto.request.CommentReplyUpdateCommand;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.service.PostReader;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.exception.constant.CommentErrorCode;
import org.sopt.bofit.global.exception.constant.CommentReplyErrorCode;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentReplyService {

    private final CommentReplyReader commentReplyReader;
    private final CommentReplyWriter commentReplyWriter;

    private final CommentReplyImageReader commentReplyImageReader;
    private final CommentReplyImageWriter commentReplyImageWriter;

    private final CommentReader commentReader;
    private final UserReader userReader;
    private final PostReader postReader;

    /**
     *  추후 이미지 개수가 많아지면 AllInBatch 등으로 수정하기
     */
    @Transactional
    public CommentReply create(Long userId, Long postId, Long commentId, CommentReplyCreateCommand command){
        Comment comment = commentReader.getActiveById(commentId);
        User user = userReader.getActiveById(userId);
        Post post = postReader.getActiveById(postId);

        comment.checkPost(post);

        CommentReply commentReply = commentReplyWriter.create(comment, user, command.content());
        IntStream.range(0, command.imageUrls().size())
                .forEach(sequence ->
                    commentReplyImageWriter.create(commentReply, command.imageUrls().get(sequence), sequence+ 1));
        return commentReply;
    }

    @Transactional
    public CommentReply update(
        Long userId,
        Long postId,
        Long commentId,
        Long commentReplyId,
        CommentReplyUpdateCommand command
    ){
        Comment comment = commentReader.getActiveById(commentId);
        User requestUser = userReader.getActiveById(userId);
        Post post = postReader.getActiveById(postId);
        CommentReply commentReply = commentReplyReader.getActiveById(commentReplyId);

        validRelation(requestUser, post, comment, commentReply);

        Map<Long, CommentReplyImage> commentReplyImageMap = commentReplyImageReader.getActiveImagesAsMap(commentReply);

        validImageIds(commentReplyImageMap.keySet(),
            command.updatedImages().stream()
                .filter(image -> image.id() != null)
                .map(UpdateImageRequest::id).toList(),
            command.deleteImageIds());
        commentReplyImageWriter.softDelete(commentReplyImageMap, command.deleteImageIds());
        commentReplyImageWriter.updateAll(commentReply, commentReplyImageMap, command.updatedImages());
        command.content().ifPresent(commentReply::updateContent);

        return commentReply;
    }

    private void validRelation(User requestUser, Post post, Comment comment, CommentReply commentReply){
        comment.checkPost(post);
        commentReply.checkComment(comment);
        commentReply.getUser().checkIsWriter(requestUser, CommentReplyErrorCode.COMMENT_REPLY_UNAUTHORIZED);
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
