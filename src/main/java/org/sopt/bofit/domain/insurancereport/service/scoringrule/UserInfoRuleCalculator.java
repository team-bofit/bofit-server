package org.sopt.bofit.domain.insurancereport.service.scoringrule;

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
import org.sopt.bofit.domain.user.util.UserUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

	private final static Map<UserInfoRuleType, Predicate<User>> USER_INFO_RULE_MAP = Map.of(
		UserInfoRuleType.OLDER_THAN_50, user -> UserUtil.convertInternationalAge(user.getBirthDate()) > 50,
		UserInfoRuleType.FEMALE, user -> user.getGender().equals(Gender.FEMALE),
		UserInfoRuleType.PRODUCTION_SITE, user -> user.getJob().equals(Job.PRODUCTION_SITE),
		UserInfoRuleType.DRIVER_DELIVERY, user -> user.getJob().equals(DRIVER_DELIVERY),
		UserInfoRuleType.MARRIED, User::isMarried,
		UserInfoRuleType.HAS_CHILD, User::isHasChild,
		UserInfoRuleType.DRIVER, User::isDriver
	);

	public double calculate(User user, InsuranceProduct product){
		return USER_INFO_RULE_MAP.entrySet().stream()
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

	private double pointFromRule (InsuranceProduct product, UserInfoRuleType type){
		List<UserInfoScoringRule> rules = scoringRuleProvider.findAll(type);
		return rules.stream()
			.mapToDouble(rule -> ScoringRuleUtil.getPoint(rule, product))
			.sum();
	}

}
