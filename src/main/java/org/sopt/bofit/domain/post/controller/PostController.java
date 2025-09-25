package org.sopt.bofit.domain.post.controller;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.COMMENTS_DEFAULT_SIZE;
import static org.sopt.bofit.domain.commentreply.constant.CommentReplyConstant.COMMENT_REPLY_DEFAULT_SIZE;
import static org.sopt.bofit.domain.post.constant.PostConstant.POSTS_DEFAULT_SIZE;
import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_DEFAULT_SIZE;
import static org.sopt.bofit.domain.post.constant.TrendPostConstant.TREND_POST_DEFAULT_SORT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_COMMENT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_COMMENT_REPLY;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_POST;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_POST_LIKE;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DEFAULT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DELETE_COMMENT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DELETE_COMMENT_REPLY;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DELETE_POST;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DELETE_POST_LIKE;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.POST_DETAIL;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.UPDATE_COMMENT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.UPDATE_COMMENT_REPLY;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.UPDATE_POST;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_DESCRIPTION_COMMUNITY;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_NAME_COMMUNITY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.dto.request.CommentCreateRequest;
import org.sopt.bofit.domain.comment.dto.request.CommentUpdateRequest;
import org.sopt.bofit.domain.comment.dto.response.CommentWithImagesResponse;
import org.sopt.bofit.domain.comment.service.CommentService;
import org.sopt.bofit.domain.commentreply.dto.request.CommentReplyCreateRequest;
import org.sopt.bofit.domain.commentreply.dto.request.CommentReplyUpdateRequest;
import org.sopt.bofit.domain.commentreply.dto.response.CommentReplyWithImagesResponse;
import org.sopt.bofit.domain.commentreply.service.CommentReplyService;
import org.sopt.bofit.domain.post.dto.request.PostCreateRequest;
import org.sopt.bofit.domain.post.dto.request.PostUpdateRequest;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.dto.response.TrendingPostsResponses;
import org.sopt.bofit.domain.post.entity.constant.PostCategoryFilter;
import org.sopt.bofit.domain.post.entity.constant.PostSortOrder;
import org.sopt.bofit.domain.post.service.PostLikeService;
import org.sopt.bofit.domain.post.service.PostService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostLikeService postLikeService;
    private final CommentReplyService commentReplyService;

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 작성", description = "커뮤니티에 글을 작성합니다.")
    @CustomExceptionDescription(CREATE_POST)
    @PostMapping()
    public BaseResponse<PostCreateResponse> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(hidden = true) @LoginUserId Long userId
    ){
        return BaseResponse.create(postService.createPost(userId, request.toCommand()),"게시물 생성 완료");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 수정", description = "커뮤니티에서 글을 수정합니다.")
    @CustomExceptionDescription(UPDATE_POST)
    @PutMapping("{post-id}")
    public BaseResponse<PostCreateResponse> updatePost(
            @RequestBody @Valid PostUpdateRequest request,
            @Parameter(hidden = true) @LoginUserId Long userId,
            @PathVariable(name = "post-id") Long postId
    ){
        return BaseResponse.ok(postService.updatePost(userId, postId, request.toCommand()),"게시물 수정 완료");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 삭제", description = "커뮤니티에서 글을 삭제합니다.(소프트 딜리트)")
    @CustomExceptionDescription(DELETE_POST)
    @DeleteMapping("{post-id}")
    public BaseResponse<Void> deletePost(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @PathVariable(name = "post-id") Long postId
    ){
        postService.deletePost(userId,postId);
        return BaseResponse.ok("게시물 삭제 완료");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 전체 조회", description = "커뮤니티에서 모든 글을 조회합니다.")
    @GetMapping()
    public BaseResponse<SliceResponse<PostSummaryResponse, Long>> getAllPosts(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestParam(required = false, defaultValue = "LATEST") PostSortOrder sort,
            @RequestParam (required = false, defaultValue = "ALL") PostCategoryFilter category,
            @RequestParam(required = false, name = "cursor") Long cursorId,
            @RequestParam(required = false, defaultValue = POSTS_DEFAULT_SIZE) int size){
        return BaseResponse.ok(postService.getAllPosts(sort, category, userId, cursorId, size), "게시물 전체 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 상세 조회", description = "커뮤니티에서 글을 상세 조회합니다.")
    @CustomExceptionDescription(POST_DETAIL)
    @GetMapping("{post-id}")
    public BaseResponse<PostDetailResponse> getPostDetail(
        @PathVariable(name = "post-id") Long postId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        return BaseResponse.ok(postService.getPostDetail(userId, postId),"글 상세 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "인기 게시물 목록 조회", description = "커뮤니티에서 인기글 목록을 조회합니다.")
    @GetMapping("/trend")
    public BaseResponse<TrendingPostsResponses> getTrendPosts(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(defaultValue = TREND_POST_DEFAULT_SIZE, required = false) @Max(10) int size,
        @RequestParam(defaultValue = TREND_POST_DEFAULT_SORT, required = false) String sort
    ){
        return BaseResponse.ok(postService.getTrendingPosts(userId, size, sort),"인기글 목록 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "댓글 작성", description = "커뮤니티 게시글에 댓글을 작성합니다.")
    @CustomExceptionDescription(CREATE_COMMENT)
    @PostMapping("/{post-id}/comments")
    public BaseResponse<PostCreateResponse> createComment(
        @RequestBody @Valid CommentCreateRequest request,
        @PathVariable(name = "post-id") Long postId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        commentService.createComment(userId, postId, request.toCommand());
        return BaseResponse.create("댓글 생성 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "댓글 수정", description = "커뮤니티 게시글의 댓글을 수정합니다.")
    @CustomExceptionDescription(UPDATE_COMMENT)
    @PatchMapping("/{post-id}/comments/{comment-id}")
    public BaseResponse<PostCreateResponse> updateComment(
        @RequestBody @Valid CommentUpdateRequest request,
        @PathVariable(name = "post-id") Long postId,
        @PathVariable(name = "comment-id") Long commentId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        commentService.updateComment(userId, postId, commentId, request.toCommand());
        return BaseResponse.create("댓글 수정 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "댓글 삭제", description = "커뮤니티 게시글의 댓글을 삭제합니다.")
    @CustomExceptionDescription(DELETE_COMMENT)
    @DeleteMapping("/{post-id}/comments/{comment-id}")
    public BaseResponse<Void> deleteComment(
        @PathVariable(name = "post-id") Long postId,
        @PathVariable(name = "comment-id") Long commentId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        commentService.deleteComment(userId, postId, commentId);
        return BaseResponse.ok("댓글 삭제 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "댓글 목록 조회 ", description = "커뮤니티 게시글의 댓글 목록을 조회합니다.")
    @CustomExceptionDescription(DELETE_COMMENT)
    @GetMapping("/{post-id}/comments")
    public BaseResponse<SliceResponse<CommentWithImagesResponse,Long>> getComments(
        @PathVariable(name = "post-id") Long postId,
        @RequestParam(required = false, name = "cursor") Long cursorId,
        @RequestParam(defaultValue = COMMENTS_DEFAULT_SIZE) int size,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        SliceResponse<CommentWithImagesResponse,Long> response
            = commentService.findAllByPostIdAndCursor(postId, userId, Optional.ofNullable(cursorId), size);
        return BaseResponse.ok(response, "댓글 목록 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시글 좋아요 생성", description = "유저가 커뮤니티 게시글에 좋아요를 생성합니다.")
    @CustomExceptionDescription(CREATE_POST_LIKE)
    @PostMapping("/{post-id}/likes")
    public BaseResponse<Void> createPostLike(
        @PathVariable(name = "post-id") Long postId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        postLikeService.createPostLike(userId, postId);
        return BaseResponse.create("좋아요 추가 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시글 좋아요 삭제", description = "유저가 커뮤니티 게시글에 생성했던 좋아요를 삭제합니다.")
    @CustomExceptionDescription(DELETE_POST_LIKE)
    @DeleteMapping("/{post-id}/likes")
    public BaseResponse<Void> deletePostLike(
        @PathVariable(name = "post-id") Long postId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        postLikeService.deletePostLike(userId, postId);
        return BaseResponse.ok("좋아요 삭제 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "대댓글 작성", description = "유저가 커뮤니티 댓글에 대댓글을 작성합니다.")
    @CustomExceptionDescription(CREATE_COMMENT_REPLY)
    @PostMapping("/{post-id}/comments/{comment-id}/reply")
    public BaseResponse<Void> createCommentReply(
        @PathVariable(name = "post-id") Long postId,
        @PathVariable(name = "comment-id") Long commentId,
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid CommentReplyCreateRequest request
    ){
        commentReplyService.create(userId, postId, commentId, request.toCommand());
        return BaseResponse.create("대댓글 작성 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "대댓글 조회", description = "커뮤니티 댓글의 대댓글을 조회합니다.")
    @GetMapping("/{post-id}/comments/{comment-id}/reply")
    public BaseResponse<SliceResponse<CommentReplyWithImagesResponse, Long>> getCommentReply(
        @PathVariable(name = "post-id") Long postId,
        @PathVariable(name = "comment-id") Long commentId,
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(required = false, name = "cursor") Long cursorId,
        @RequestParam(defaultValue = COMMENT_REPLY_DEFAULT_SIZE) int size
    ){
        SliceResponse<CommentReplyWithImagesResponse, Long> response =
            commentReplyService.findAllWithCursor(postId, commentId, userId, Optional.ofNullable(cursorId), size);
        return BaseResponse.ok(response, "대댓글 목록 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "대댓글 수정", description = "유저가 커뮤니티 댓글의 대댓글을 수정합니다.")
    @CustomExceptionDescription(UPDATE_COMMENT_REPLY)
    @PatchMapping("/{post-id}/comments/{comment-id}/reply/{comment-reply-id}")
    public BaseResponse<Void> updateCommentReply(
        @PathVariable(name = "post-id") Long postId,
        @PathVariable(name = "comment-id") Long commentId,
        @PathVariable(name = "comment-reply-id") Long commentReplyId,
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid CommentReplyUpdateRequest request
    ){
        commentReplyService.update(userId, postId, commentId, commentReplyId, request.toCommand());
        return BaseResponse.ok("대댓글 수정 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "대댓글 삭제", description = "유저가 커뮤니티 댓글의 대댓글을 삭제합니다.")
    @CustomExceptionDescription(DELETE_COMMENT_REPLY)
    @DeleteMapping("/{post-id}/comments/{comment-id}/reply/{comment-reply-id}")
    public BaseResponse<Void> deleteCommentReply(
        @PathVariable(name = "post-id") Long postId,
        @PathVariable(name = "comment-id") Long commentId,
        @PathVariable(name = "comment-reply-id") Long commentReplyId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        commentReplyService.delete(userId, postId, commentId, commentReplyId);
        return BaseResponse.ok("대댓글 삭제 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 검색", description = "검색 키워드를 기반으로 게시물을 검색합니다.")
    @CustomExceptionDescription(DEFAULT)
    @GetMapping("search")
    public BaseResponse<SliceResponse<PostSummaryResponse, Long>> searchPosts(
            @RequestParam(name = "keyword") String keyword,
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestParam(required = false, name = "cursor") Long cursorId,
            @RequestParam(required = false, defaultValue = POSTS_DEFAULT_SIZE) int size
    ){
        return BaseResponse.ok(postService.searchPosts(userId, keyword, cursorId, size),"게시물 검색 성공");
    }

}
