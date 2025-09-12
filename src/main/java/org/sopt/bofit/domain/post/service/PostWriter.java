package org.sopt.bofit.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.comment.entity.Comment;
import org.sopt.bofit.domain.comment.entity.CommentStatus;
import org.sopt.bofit.domain.comment.repository.CommentRepository;
import org.sopt.bofit.domain.post.dto.response.PostCreateResponse;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.PostImage;
import org.sopt.bofit.domain.post.repository.PostImageRepository;
import org.sopt.bofit.domain.post.repository.PostRepository;
import org.sopt.bofit.domain.post.service.dto.request.PostUpdateCommand;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.file.dto.request.NewImageRequest;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_IMAGE_MISMATCH;
import static org.sopt.bofit.global.exception.constant.PostErrorCode.POST_UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class PostWriter {

    private final UserReader userReader;

    private final PostReader postReader;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostImageRepository postImageRepository;


    @Transactional
    public PostCreateResponse createPost(Long userId, String title, String content, String category, List<String> imageUrls) {
        User user = userReader.getActiveById(userId);
        Post newPost = Post.create(user, title, content, category);
        postRepository.save(newPost);

        int sequence = 1;
        List<PostImage> postImages = new ArrayList<>();

        if(imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                postImages.add(PostImage.create(imageUrl, newPost, sequence++));
            }
        }

        postImageRepository.saveAll(postImages);

        return PostCreateResponse.from(newPost.getId());
    }

    @Transactional
    public PostCreateResponse updatePost (Long userId, Long postId, PostUpdateCommand command) {
        User user = userReader.getActiveById(userId);
        Post post = postReader.getActiveById(postId);

        post.getUser().checkIsWriter(userId, POST_UNAUTHORIZED);
        post.updatePost(command.newTitle(), command.newContent(), command.newCategory());

        List<PostImage> currentImages = postImageRepository.findByPostIdOrderBySequenceAsc(postId);

        deleteImages(postId, command.deleteImageIds(), currentImages);

        updateImages(command.updateImages(), currentImages);

        addImages(command.newImages(), currentImages, post);

        int seq = 1;
        for(PostImage pi : currentImages) {
            if(pi.getSequence() != seq){
                pi.updateSequence(seq);
            }
            seq++;
        }

        return PostCreateResponse.from(post.getId());
    }

    private void addImages(List<NewImageRequest> newImages, List<PostImage> currentImages, Post post) {
        if(newImages != null){
            for(NewImageRequest req : newImages) {
                int sequence = req.sequence() == null ? (currentImages.size() + 1) : req.sequence();

                if(sequence < 1) sequence = 1;
                if(sequence > currentImages.size() + 1) sequence = currentImages.size() + 1;

                PostImage created = PostImage.create(req.imageUrl(), post, sequence);
                postImageRepository.save(created);

                currentImages.add(sequence - 1 ,created);
            }
        }
    }

    private void updateImages(List<UpdateImageRequest> updateImages, List<PostImage> currentImages) {
        if (updateImages != null && !updateImages.isEmpty()) {
            Map<Long, Integer> indexById = new HashMap<>();
            for (int i = 0; i < currentImages.size(); i++) {
                indexById.put(currentImages.get(i).getId(), i);
            }

            for (UpdateImageRequest req : updateImages) {
                Integer from = indexById.get(req.id());
                if (from == null) throw new BadRequestException(POST_IMAGE_MISMATCH);

                PostImage img = currentImages.get(from);

                if (req.newImageUrl() != null && !req.newImageUrl().isBlank()) {
                    img.updateImageUrl(req.newImageUrl());
                }

                if (req.newSequence() != null) {
                    int to = Math.max(0, Math.min(req.newSequence() - 1, currentImages.size() - 1));
                    moveSequence(currentImages, from, to);

                    indexById.clear();
                    for (int i = 0; i < currentImages.size(); i++) {
                        indexById.put(currentImages.get(i).getId(), i);
                    }
                }
            }
        }
    }

    private void deleteImages(Long postId, List<Long> deleteImageIds, List<PostImage> currentImages) {
        if(deleteImageIds != null && !deleteImageIds.isEmpty()) {
            for(Long imageId : deleteImageIds) {
                if(currentImages.stream().noneMatch(image -> image.getId().equals(imageId))) {
                    throw new BadRequestException(POST_IMAGE_MISMATCH);
                }
            }
            postImageRepository.deleteAllByPostIdAndIdIn(postId, deleteImageIds);
            currentImages.removeIf(image -> deleteImageIds.contains(image.getId()));
        }
    }

    private static <T> void moveSequence(List<T> list, int fromIdx, int toIdx) {
        if (fromIdx == toIdx) return;
        T e = list.remove(fromIdx);
        list.add(toIdx, e);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        User user = userReader.getActiveById(userId);
        Post post = postReader.getActiveById(postId);
        post.getUser().checkIsWriter(userId, POST_UNAUTHORIZED);

        postRepository.deletePostByPostId(postId);

        commentRepository.findAllByPostIdAndStatus(postId, CommentStatus.ACTIVE).forEach(Comment::softDelete);

    }
    @Transactional
    public void increaseLikeCount(Post post){
        postRepository.increaseLikeCount(post);
    }

    @Transactional
    public void decreaseLikeCount(Post post){
        postRepository.decreaseLikeCount(post);
    }
}
