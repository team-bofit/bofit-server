package org.sopt.bofit.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.entity.QComment;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.QPost;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.user.dto.response.MyPostsResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sopt.bofit.domain.post.entity.QPost.post;


@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<MyPostsResponse> findPostsByCursorId(Long userId, Long cursorId, int size) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        List<MyPostsResponse> content = queryFactory
                .select(Projections.constructor(MyPostsResponse.class,
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
}

