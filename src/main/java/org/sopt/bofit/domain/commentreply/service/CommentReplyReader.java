package org.sopt.bofit.domain.commentreply.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.commentreply.entity.CommentReply;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyStatus;
import org.sopt.bofit.domain.commentreply.repository.CommentReplyRepository;
import org.sopt.bofit.domain.commentreply.service.dto.response.CommentReplyResponse;
import org.sopt.bofit.global.exception.constant.CommentReplyErrorCode;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReplyReader {

    private final CommentReplyRepository commentReplyRepository;

    public CommentReply getActiveById(Long commentReplyId) {
        return commentReplyRepository.findByIdAndStatus(commentReplyId, CommentReplyStatus.ACTIVE)
            .orElseThrow(() -> new NotFoundException(CommentReplyErrorCode.COMMENT_REPLY_NOT_FOUND));
    }

    public Slice<CommentReplyResponse> getAllActiveCommentReply(Comment comment, Optional<Long> cursor, int size){
        return commentReplyRepository.findActivesByComment(comment, cursor, size);
    }

}
