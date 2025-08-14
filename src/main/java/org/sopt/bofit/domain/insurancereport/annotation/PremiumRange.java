package org.sopt.bofit.domain.insurancereport.annotation;

import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.sopt.bofit.domain.insurancereport.annotation.resolver.PremiumRangeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PremiumRangeValidator.class)
public @interface PremiumRange {
	String message() default "최소 보험료는 최대 보험료보다 {range} 원 이상 적어야 합니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	int range() default PREMIUM_RANGE;
}
