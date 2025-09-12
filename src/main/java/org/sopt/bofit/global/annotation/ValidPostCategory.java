package org.sopt.bofit.global.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.sopt.bofit.global.util.validator.PostCategoryValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostCategoryValidator.class)
@Documented
public @interface ValidPostCategory {
    String message() default "유효한 게시물 카테고리가 아닙니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
