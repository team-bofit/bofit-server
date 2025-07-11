package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.sopt.bofit.domain.insurancereport.constant.ScoringRuleConstant.*;
import static org.sopt.bofit.domain.user.entity.constant.Job.*;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoScoringRule;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	public double calculate(User user, InsuranceProduct product, int age){
		return getUserInfoRuleMap(age).entrySet().stream()
			.mapToDouble(entry ->  considerUserInfo(entry.getValue(), user, product, entry.getKey()))
			.sum();
	}

	private double considerUserInfo (Predicate<User> condition, User user, InsuranceProduct product, UserInfoRuleType ruleType) {
		double additional = 0;
		if (condition.test(user)) {
			additional += pointFromRule(product, ruleType);
		}
		return additional;
	}

	private Map<UserInfoRuleType, Predicate<User>> getUserInfoRuleMap(int age){
		return Map.of(
			UserInfoRuleType.AT_RISK_OF_MAJOR_DISEASE, user -> age > MAJOR_DISEASE_RISKED_AGE,
			UserInfoRuleType.FEMALE, user -> user.getGender().equals(Gender.FEMALE),
			UserInfoRuleType.PRODUCTION_SITE, user -> user.getJob().equals(Job.PRODUCTION_SITE),
			UserInfoRuleType.DRIVER_DELIVERY, user -> user.getJob().equals(DRIVER_DELIVERY),
			UserInfoRuleType.MARRIED, User::isMarried,
			UserInfoRuleType.HAS_CHILD, User::isHasChild,
			UserInfoRuleType.DRIVER, User::isDriver
		);
	}

	private double pointFromRule (InsuranceProduct product, UserInfoRuleType type){
		List<UserInfoScoringRule> rules = scoringRuleProvider.findAllUserInfoRuleType(type);
		return rules.stream()
			.mapToDouble(rule -> ScoringRuleUtil.getPoint(rule, product))
			.sum();
	}

}
