package org.sopt.bofit.domain.post.controller;

import static org.sopt.bofit.domain.comment.constant.CommentConstant.COMMENTS_DEFAULT_SIZE;
import static org.sopt.bofit.domain.post.constant.PostConstant.POSTS_DEFAULT_SIZE;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_COMMENT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_POST;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.CREATE_POST_LIKE;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DELETE_COMMENT;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.DELETE_POST;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.POST_DETAIL;
import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.UPDATE_POST;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_DESCRIPTION_COMMUNITY;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_NAME_COMMUNITY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.dto.request.CommentCreateRequest;
import org.sopt.bofit.domain.comment.dto.response.CommentResponse;
import org.sopt.bofit.domain.comment.service.CommentService;
import org.sopt.bofit.domain.post.dto.request.PostCreateRequest;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.service.PostLikeService;
import org.sopt.bofit.domain.post.service.PostService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 작성", description = "커뮤니티에 글을 작성합니다.")
    @CustomExceptionDescription(CREATE_POST)
    @PostMapping()
    public BaseResponse<PostCreateResponse> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(hidden = true) @LoginUserId Long userId
    ){
        return BaseResponse.create(postService.createPost(userId, request.title(), request.content()),"게시물 생성 완료");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 수정", description = "커뮤니티에서 글을 수정합니다.")
    @CustomExceptionDescription(UPDATE_POST)
    @PutMapping("{post-id}")
    public BaseResponse<PostCreateResponse> updatePost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(hidden = true) @LoginUserId Long userId,
            @PathVariable(name = "post-id") Long postId
    ){
        return BaseResponse.ok(postService.updatePost(userId,postId,request.title(), request.content()),"게시물 수정 완료");
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
            @RequestParam(required = false, name = "cursor") Long cursorId,
            @RequestParam(required = false, defaultValue = POSTS_DEFAULT_SIZE) int size){
        return BaseResponse.ok(postService.getAllPosts(cursorId, size), "게시물 전체 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시물 상세 조회", description = "커뮤니티에서 글을 상세 조회합니다.")
    @CustomExceptionDescription(POST_DETAIL)
    @GetMapping("{post-id}")
    public BaseResponse<PostDetailResponse> getPostDetail(
            @PathVariable(name = "post-id") Long postId
    ){
        return BaseResponse.ok(postService.getPostDetail(postId),"글 상세 조회 성공");
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
        commentService.createComment(userId, postId, request);
        return BaseResponse.create("댓글 생성 성공");
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
    public BaseResponse<SliceResponse<CommentResponse,Long>> getComments(
        @PathVariable(name = "post-id") Long postId,
        @RequestParam(required = false, name = "cursor") Long cursorId,
        @RequestParam(defaultValue = COMMENTS_DEFAULT_SIZE) int size,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        SliceResponse<CommentResponse,Long> response = commentService.findAllByPostIdAndCursor(postId, userId, Optional.ofNullable(cursorId), size);
        return BaseResponse.ok(response, "댓글 목록 조회 성공");
    }

    @Tag(name = TAG_NAME_COMMUNITY, description = TAG_DESCRIPTION_COMMUNITY)
    @Operation(summary = "게시글 좋아요 생성", description = "유저가 커뮤니티 게시글에 좋아요를 생성합니다.")
    @CustomExceptionDescription(CREATE_POST_LIKE)
    @PostMapping("/{post-id}/likes")
    public BaseResponse<?> createPostLike(
        @PathVariable(name = "post-id") Long postId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        postLikeService.createPostLike(userId, postId);
        return BaseResponse.create("좋아요 추가 성공");
    }
}
