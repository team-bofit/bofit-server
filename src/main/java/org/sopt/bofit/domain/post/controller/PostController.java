package org.sopt.bofit.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.sopt.bofit.domain.comment.dto.request.CommentCreateRequest;
import org.sopt.bofit.domain.comment.service.CommentService;
import org.sopt.bofit.domain.post.dto.request.PostCreateRequest;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.dto.response.PostDetailResponse;
import org.sopt.bofit.domain.post.dto.response.PostSummaryResponse;
import org.sopt.bofit.domain.post.service.PostService;
import org.sopt.bofit.domain.user.dto.response.SliceResponse;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @Tag(name = "Community", description = "커뮤니티 관련 API")
    @Operation(summary = "게시물 작성", description = "커뮤니티에 글을 작성합니다.")
    @CustomExceptionDescription(CREATE_POST)
    @PostMapping("post")
    public BaseResponse<PostCreateResponse> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(hidden = true) @LoginUserId Long userId
    ){
        return BaseResponse.ok(postService.createPost(userId, request.title(), request.content()),"게시물 생성 완료");
    }

    @Tag(name = "Community", description = "커뮤니티 관련 API")
    @Operation(summary = "게시물 수정", description = "커뮤니티에서 글을 수정합니다.")
    @CustomExceptionDescription(UPDATE_POST)
    @PutMapping("{postId}")
    public BaseResponse<PostCreateResponse> updatePost(
            @RequestBody @Valid PostCreateRequest request,
            @Parameter(hidden = true) @LoginUserId Long userId,
            @PathVariable Long postId
    ){
        return BaseResponse.ok(postService.updatePost(userId,postId,request.title(), request.content()),"게시물 수정 완료");
    }

    @Tag(name = "Community", description = "커뮤니티 관련 API")
    @Operation(summary = "게시물 삭제", description = "커뮤니티에서 글을 삭제합니다.(소프트 딜리트)")
    @CustomExceptionDescription(DELETE_POST)
    @DeleteMapping("{postId}")
    public BaseResponse<Void> deletePost(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @PathVariable Long postId
    ){
        postService.deletePost(userId,postId);
        return BaseResponse.ok("게시물 삭제 완료");
    }

    @Tag(name = "Community", description = "커뮤니티 관련 API")
    @Operation(summary = "게시물 전체 조회", description = "커뮤니티에서 모든 글을 조회합니다.")
    @GetMapping("")
    public BaseResponse<SliceResponse<PostSummaryResponse>> getAllPosts(
            @RequestParam(required = false) Long cursorId,
            @RequestParam int size){
        return BaseResponse.ok(postService.getAllPosts(cursorId, size), "게시물 전체 조회 성공");
    }

    @Tag(name = "Community", description = "커뮤니티 관련 API")
    @Operation(summary = "게시물 상세 조회", description = "커뮤니티에서 글을 상세 조회합니다.")
    @CustomExceptionDescription(POST_DETAIL)
    @GetMapping("{postId}")
    public BaseResponse<PostDetailResponse> getPostDetail(
            @PathVariable Long postId
    ){
        return BaseResponse.ok(postService.getPostDetail(postId),"글 상세 조회 성공");
    }

    @Tag(name = "Community", description = "커뮤니티 관련 API")
    @Operation(summary = "댓글 작성", description = "커뮤니티 게시글에 댓글을 작성합니다.")
    @CustomExceptionDescription(CREATE_POST)
    @PostMapping("/{post-id}/comments")
    public BaseResponse<PostCreateResponse> createComment(
        @RequestBody @Valid CommentCreateRequest request,
        @PathVariable(name = "post-id") Long postId,
        @Parameter(hidden = true) @LoginUserId Long userId
    ){
        commentService.createComment(userId, postId, request);
        return BaseResponse.ok("댓글 생성 성공");
    }
}
