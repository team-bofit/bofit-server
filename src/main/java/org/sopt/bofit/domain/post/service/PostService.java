package org.sopt.bofit.domain.post.service;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_UNAUTHORIZED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.dto.response.TrendingPostsResponses;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.entity.TrendPost;
import org.sopt.bofit.domain.post.entity.constant.TrendPostSort;
import org.sopt.bofit.domain.post.service.dto.request.PostCreateCommand;
import org.sopt.bofit.domain.post.service.dto.request.PostUpdateCommand;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.cache.CacheService;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.sopt.bofit.global.file.util.ImageValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostReader postReader;
    private final PostWriter postWriter;

    private final PostLikeReader postLikeReader;

    private final UserReader userReader;

    private final PostImageWriter postImageWriter;
    private final PostImageReader postImageReader;

    private final TrendPostReader trendPostReader;
    private final TrendPostWriter trendPostWriter;

    private final CacheService cacheService;

    @Transactional
    public PostCreateResponse createPost(Long userId, PostCreateCommand command) {
        User user = userReader.getActiveById(userId);

        Post post = postWriter.createPost(userId, command.title(), command.content(), command.category());

        IntStream.range(0, command.imageUrls().size())
                .forEach(sequence -> postImageWriter
                        .create(post, command.imageUrls().get(sequence), sequence + 1));

        return PostCreateResponse.from(post.getId());
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, PostUpdateCommand command) {
        User user =  userReader.getActiveById(userId);
        Post post = postReader.getActiveById(postId);

        post.getUser().checkIsWriter(userId, POST_UNAUTHORIZED);

        post.updatePost(command.title(),command.content(), command.category());

        Map<Long, PostImage> postImageMap = postImageReader.getActiveImageAsMap(post);

        postImageWriter.softDelete(postImageMap, command.deleteImageIds());
        postImageWriter.updateAll(post, postImageMap, command.updatedImages());

        ImageValidator.validImageIds(postImageMap.keySet(), command.updatedImages().stream()
                        .filter(image -> image.id() != null).map(UpdateImageRequest::id).toList(),
                command.deleteImageIds());

        return PostCreateResponse.from(post.getId());
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        postWriter.deletePost(userId, postId);
    }

    public SliceResponse<PostSummaryResponse, Long> getAllPosts(Long userId, Long cursorId, int size){
        return postReader.getAllPosts(userId, cursorId, size);
    }

    public PostDetailResponse getPostDetail(Long userId, Long postId){
        return postReader.getPostById(userId, postId);
    }

    public SliceResponse<PostSummaryResponse, Long> searchPosts(Long userId, String keyword, Long cursorId, int size){
        return postReader.findPostsByKeywordAndCursorId(userId, keyword, cursorId, size);
    }

    @Transactional
    public TrendingPostsResponses getTrendingPosts(Long userId, int size, String sort){
        User user = userReader.getActiveById(userId);

        List<Post> cachedTrendingPosts = cacheService.getTrendingPosts();
        List<Post> trendingPosts = cachedTrendingPosts.size() < size ?
            getNewTrendingPosts(size) : cachedTrendingPosts;

        List<Post> targetTrendingPosts = getTargetTrendingPosts(sort, size, trendingPosts);
        Map<Post, Boolean> isLiked = postLikeReader.isLikedPosts(user, targetTrendingPosts);

        return TrendingPostsResponses.of(targetTrendingPosts, isLiked);
    }

    private List<Post> getNewTrendingPosts(int size){
        List<TrendPost> trendPosts = trendPostReader.getAll();

        if(trendPosts.size() < size){
            trendPostWriter.updateTrendPosts();
            trendPosts = trendPostReader.getAll();
        }

        return trendPostReader.getTrendingPosts(trendPosts);
    }

    private List<Post> getTargetTrendingPosts(String sort, int size, List<Post> trendingPosts){
        if (TrendPostSort.from(sort).equals(TrendPostSort.RANDOM)){
            ArrayList<Post> shallowTrendingPosts = new ArrayList<>(trendingPosts);
            Collections.shuffle(shallowTrendingPosts);
            return shallowTrendingPosts.subList(0, size);
        }

        return trendingPosts.subList(0, size);
    }

}
