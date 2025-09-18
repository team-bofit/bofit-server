package org.sopt.bofit.domain.commentreply.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.commentreply.entity.CommentReplyStatus;
import org.sopt.bofit.domain.commentreply.entity.QCommentReply;
import org.sopt.bofit.domain.commentreply.service.dto.response.CommentReplyResponse;
import org.sopt.bofit.domain.user.entity.QUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentReplyCustomRepositoryImpl implements CommentReplyCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    private final QCommentReply commentReply = QCommentReply.commentReply;
    private final QUser user = QUser.user;

    @Override
    public Slice<CommentReplyResponse> findActivesByComment(
        Comment targetComment,
        Optional<Long> cursor,
        int size
    ) {

        List<CommentReplyResponse> results = jpaQueryFactory
            .selectDistinct(Projections.constructor(CommentReplyResponse.class,
                commentReply.id,
                user.id,
                user.nickname,
                user.profileImage,
                commentReply.content,
                commentReply.createdAt,
                commentReply.updatedAt
            ))
            .from(commentReply)
            .leftJoin(commentReply.user, user)
            .where(
                commentReply.comment.eq(targetComment),
                commentReply.status.eq(CommentReplyStatus.ACTIVE),
                cursor.map(commentReply.id::gt).orElse(null)
            )
            .groupBy(commentReply.id)
            .orderBy(commentReply.id.asc())
            .limit(size + 1)
            .fetch();

        boolean hasNext = results.size() > size;
        if (hasNext) results.remove(size);

        return new SliceImpl<>(results, PageRequest.of(0, size), hasNext);
    }

}
