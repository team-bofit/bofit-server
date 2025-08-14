package org.sopt.bofit.domain.insurancereport.annotation.resolver;

import org.sopt.bofit.domain.insurancereport.annotation.PremiumRange;
import org.sopt.bofit.domain.insurancereport.dto.request.InsuranceReportRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PremiumRangeValidator implements ConstraintValidator<PremiumRange, InsuranceReportRequest> {

	int range;

	@Override
	public void initialize(PremiumRange constraintAnnotation) {
		range = constraintAnnotation.range();
	}

	@Override
	public boolean isValid(InsuranceReportRequest request, ConstraintValidatorContext constraintValidatorContext) {
		if(request == null){
			return false;
		}

		return request.maxPremium() - request.minPremium() >= range;
	}
}
