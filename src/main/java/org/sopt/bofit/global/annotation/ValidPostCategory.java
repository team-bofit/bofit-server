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
    String message() default "게시물 카테고리는 QNA, CONVERSATION, INFORMATION 중에서 선택해주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
