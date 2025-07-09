package org.sopt.bofit.domain.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.user.dto.response.MyCommentsResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.sopt.bofit.domain.comment.entity.QComment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<MyCommentsResponse> findCommentsByCursorId(Long userId, Long cursorId, int size) {
        QComment comment = QComment.comment;

        List<MyCommentsResponse> content = queryFactory
                .select(Projections.constructor(MyCommentsResponse.class,
                        comment.id,
                        comment.post.id,
                        comment.content,
                        comment.createdAt
                ))
                .from(comment)
                .where(
                        comment.user.id.eq(userId),
                        comment.status.eq(CommentStatus.ACTIVE),
                        cursorId != null ? comment.id.lt(cursorId) : null
                )
                .groupBy(comment.id)
                .orderBy(comment.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = content.size() > size;
        if (hasNext) content.remove(size);

        return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);
    }
}
