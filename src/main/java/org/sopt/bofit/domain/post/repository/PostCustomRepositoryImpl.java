package org.sopt.bofit.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.entity.QComment;
import org.sopt.bofit.domain.post.dto.response.PostListResponse;
import org.sopt.bofit.domain.post.entity.QPost;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.user.dto.response.PostSummaryResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PostSummaryResponse> findPostsByCursorId(Long userId, Long cursorId, int size) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        List<PostSummaryResponse> content = queryFactory
                .select(Projections.constructor(PostSummaryResponse.class,
                        post.id,
                        post.title,
                        post.content,
                        comment.id.count().intValue(),
                        post.createdAt
                ))
                .from(post)
                .leftJoin(comment).on(comment.post.eq(post), comment.status.eq(CommentStatus.ACTIVE))
                .where(
                        post.user.id.eq(userId),
                        post.status.eq(PostStatus.ACTIVE),
                        cursorId != null ? post.id.lt(cursorId) : null
                )
                .groupBy(post.id)
                .orderBy(post.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = content.size() > size;
        if (hasNext) content.remove(size);

        return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);
    }

    @Override
    public void deletePostByPostId(Long postId) {
        QPost post = QPost.post;

        queryFactory
                .update(post)
                .set(post.status,PostStatus.INACTIVE)
                .where(post.id.eq(postId))
                .execute();
    }

    @Override
    public Slice<PostListResponse> findAllByCursorId(Long cursorId, int size) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        List<PostListResponse> content = queryFactory
                .select(Projections.constructor(PostListResponse.class,
                        post.id,
                        post.user.id,
                        post.title,
                        post.content,
                        post.user.nickname,
                        post.user.profileImage,
                        comment.id.count().intValue(),
                        post.createdAt
                ))
                .from(post)
                .leftJoin(comment).on(comment.post.eq(post), comment.status.eq(CommentStatus.ACTIVE))
                .where(
                        post.status.eq(PostStatus.ACTIVE),
                        cursorId != null ? post.id.lt(cursorId) : null
                )
                .groupBy(post.id)
                .orderBy(post.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = content.size() > size;
        if (hasNext) content.remove(size);

        return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);



    }
}

