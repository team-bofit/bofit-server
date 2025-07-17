package org.sopt.bofit.domain.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.entity.QComment;
import org.sopt.bofit.domain.user.dto.response.MyCommentSummaryResponse;
import org.sopt.bofit.domain.user.entity.QUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QComment comment = QComment.comment;
    private final QUser user = QUser.user;

    @Override
    public Slice<MyCommentSummaryResponse> findCommentsByCursorId(Long userId, Long cursorId, int size) {


        List<MyCommentSummaryResponse> content = queryFactory
                .select(Projections.constructor(MyCommentSummaryResponse.class,
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


    @Override
    public Slice<CommentResponse> findActivesByPostIdWithCursor(
        Long postId,
        Optional<Long> cursor,
        int size
    ) {
        List<CommentResponse> content = queryFactory
            .select(Projections.constructor(CommentResponse.class,
                comment.id,
                comment.user.id,
                user.nickname,
                user.profileImage,
                comment.content,
                comment.createdAt,
                comment.updatedAt
            ))
            .from(comment)
            .leftJoin(user).on(user.eq(comment.user))
            .where(
                comment.post.id.eq(postId),
                comment.status.eq(CommentStatus.ACTIVE),
				cursor.map(comment.id::gt).orElse(null)
            )
            .groupBy(comment.id)
            .orderBy(comment.id.asc())
            .limit(size + 1)
            .fetch();

        boolean hasNext = content.size() > size;
        if (hasNext) content.remove(size);

        return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);
    }
}
