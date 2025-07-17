package org.sopt.bofit.domain.insurancereport.util;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;

public class ScoringRuleUtil {

	public static double getPoint (InsuranceScoringRule scoringRule, InsuranceProduct product){
		int coverage = scoringRule.getConditionCoverage().getCoverage(product);
		return switch (scoringRule.getConditionOperator()){
			case GE -> coverage >= scoringRule.getConditionValue() ? scoringRule.getPoint() : 0;
			case EXIST -> coverage > 0 ? scoringRule.getPoint() : 0;
			case MINUS -> scoringRule.getPoint() * (coverage / 1000.0);
			default -> throw new InternalException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "올바르지 않은 ConditionOperator 입니다.");
		};
	}
}
