package org.sopt.bofit.domain.post.dto.response;

import org.sopt.bofit.domain.post.entity.constant.PostCategory;

public record PostCategoryResponse (
    PostCategory category,
    String description
){

    public static PostCategoryResponse from(PostCategory postCategory){
        return new PostCategoryResponse(postCategory, postCategory.getDescription());
    }
}
