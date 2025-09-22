package org.sopt.bofit.domain.post.repository;

import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_COMMENT_REPLY_WEIGHT;
import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_COMMENT_WEIGHT;
import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_LIKE_WEIGHT;
import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_SCORED_DATE_RANGE;
import static org.sopt.bofit.domain.post.entity.constant.PostStatus.ACTIVE;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.QComment;
import org.sopt.bofit.domain.commentreply.entity.QCommentReply;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.QPost;
import org.sopt.bofit.domain.post.entity.QPostLike;
import org.sopt.bofit.domain.post.entity.constant.PostStatus;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<MyPostSummaryResponse> findPostsByCursorId(Long userId, Long cursorId, int size) {
        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;

        BooleanExpression likedByCurrentUser = getLikedByCurrentUser(userId, postLike, post);

        List<MyPostSummaryResponse> content = queryFactory
                .select(Projections.constructor(MyPostSummaryResponse.class,
                        post.id,
                        post.title,
                        post.content,
                        post.commentCount,
                        post.createdAt,
                        post.likeCount,
                        likedByCurrentUser
                ))
                .from(post)
                .where(
                        post.user.id.eq(userId),
                        post.status.eq(ACTIVE),
                        cursorId != null ? post.id.lt(cursorId) : null
                )
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
    public Slice<PostSummaryResponse> findAllByCursorId(Long userId, Long cursorId, int size) {
        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;

        BooleanExpression likedByCurrentUser = getLikedByCurrentUser(userId, postLike, post);

        List<PostSummaryResponse> content = queryFactory
                .select(Projections.constructor(PostSummaryResponse.class,
                        post.id,
                        post.user.id,
                        post.title,
                        post.content,
                        post.user.nickname,
                        post.user.profileImage,
                        post.commentCount,
                        post.createdAt,
                        post.likeCount,
                        likedByCurrentUser
                ))
                .from(post)
                .where(
                        post.status.eq(PostStatus.ACTIVE),
                        cursorId != null ? post.id.lt(cursorId) : null
                )
                .orderBy(post.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = content.size() > size;
        if (hasNext) content.remove(size);

        return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);
    }

    private BooleanExpression getLikedByCurrentUser(Long userId, QPostLike postLike, QPost post) {
        return JPAExpressions
                .selectOne()
                .from(postLike)
                .where(postLike.post.eq(post),
                        postLike.user.id.eq(userId))
                .exists();
    }

    @Override
    public Slice<PostSummaryResponse> findAllByKeywordAndCursorId(Long userId, String keyword, Long cursorId, int size) {
        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;

        BooleanExpression likedByCurrentUser = getLikedByCurrentUser(userId, postLike, post);

        BooleanExpression searchCondition;
        if (keyword != null && keyword.length() < 2) {
            String likePattern = "%" + keyword + "%";
            searchCondition = post.title.like(likePattern)
                    .or(post.content.like(likePattern))
                    .or(post.writerNickname.like(likePattern));
        } else {
            NumberExpression<Double> relevanceScore = getRelevanceScore(keyword, post);
            searchCondition = relevanceScore.gt(0);
        }

        List<PostSummaryResponse> content = queryFactory
                .select(Projections.constructor(PostSummaryResponse.class,
                        post.id,
                        post.user.id,
                        post.title,
                        post.content,
                        post.user.nickname,
                        post.user.profileImage,
                        post.commentCount,
                        post.createdAt,
                        post.likeCount,
                        likedByCurrentUser
                ))
                .from(post)
                .where(searchCondition,
                        post.status.eq(PostStatus.ACTIVE),
                        cursorId != null ? post.id.lt(cursorId) : null
                        )
                .orderBy(keyword != null && keyword.length() > 1
                        ? getRelevanceScore(keyword, post).desc()
                        : post.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = content.size() > size;
        if (hasNext) content.remove(size);

        return new SliceImpl<>(content, PageRequest.of(0, size), hasNext);
    }

    @Override
    public List<Post> findTrendPosts(int size, LocalDateTime now) {

        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;
        QComment comment = QComment.comment;
        QCommentReply commentReply = QCommentReply.commentReply;

        NumberExpression<Integer> score =
            postLike.count().intValue().multiply(TREND_POST_LIKE_WEIGHT)
            .add(comment.count().intValue().multiply(TREND_POST_COMMENT_WEIGHT)
                .add(commentReply.count().intValue().multiply(TREND_POST_COMMENT_REPLY_WEIGHT))
                .add(post.likeCount)
                .add(post.commentCount)
            );

        LocalDateTime scoredDateRange = now.minusDays(TREND_POST_SCORED_DATE_RANGE);

        List<Post> trendPosts = queryFactory
            .select(post)
            .from(post)
            .leftJoin(postLike).on(postLike.post.eq(post), postLike.createdAt.after(scoredDateRange))
            .leftJoin(comment).on(comment.post.eq(post), comment.createdAt.after(scoredDateRange))
            .leftJoin(commentReply).on(commentReply.comment.eq(comment), commentReply.createdAt.after(scoredDateRange))
            .where(post.status.eq(PostStatus.ACTIVE))
            .orderBy(score.desc(), post.id.desc())
            .groupBy(post.id)
            .limit(size)
            .fetch();

        return trendPosts;
    }

    private NumberExpression<Double> getRelevanceScore(String keyword, QPost post) {
        return Expressions.numberTemplate(Double.class,
                "function('match_language_mode',{0},{1},{2},{3})",
                post.title, post.content, post.writerNickname, keyword
        );
    }

}

