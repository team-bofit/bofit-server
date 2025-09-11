package org.sopt.bofit.global.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sopt.bofit.domain.post.entity.constant.PostCategory;
import org.sopt.bofit.global.annotation.ValidPostCategory;

import java.util.Arrays;

public class PostCategoryValidator implements ConstraintValidator<ValidPostCategory, String> {

    @Override
    public boolean isValid(String category, ConstraintValidatorContext context) {
        return Arrays.stream(PostCategory.values())
                .anyMatch(postCategory -> postCategory.name().equalsIgnoreCase(category));
    }
}
