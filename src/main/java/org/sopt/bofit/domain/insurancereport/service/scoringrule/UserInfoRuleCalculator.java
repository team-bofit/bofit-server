package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.sopt.bofit.domain.insurancereport.constant.ScoringRuleConstant.MAJOR_DISEASE_RISKED_AGE;
import static org.sopt.bofit.domain.user.entity.constant.Job.DRIVER_DELIVERY;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoScoringRule;
import org.sopt.bofit.domain.insurancereport.util.ScoringRuleUtil;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoRuleCalculator {

	private final ScoringRuleProvider scoringRuleProvider;

    public double calculate(PersonalInfo personalInfo, InsuranceProduct product, int age){
        return getPersonalInfoRuleMap(age).entrySet().stream()
            .mapToDouble(entry ->  considerPersonalInfo(entry.getValue(), personalInfo, product, entry.getKey()))
            .sum();
    }

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

    private double considerPersonalInfo (Predicate<PersonalInfo> condition, PersonalInfo personalInfo, InsuranceProduct product, UserInfoRuleType ruleType) {
        double additional = 0;
        if (condition.test(personalInfo)) {
            additional += pointFromRule(product, ruleType);
        }
        return additional;
    }

	private Map<UserInfoRuleType, Predicate<User>> getUserInfoRuleMap(int age){
		return Map.of(
			UserInfoRuleType.AT_RISK_OF_MAJOR_DISEASE, user -> age > MAJOR_DISEASE_RISKED_AGE,
			UserInfoRuleType.FEMALE, user -> user.getPersonalInfo().getGender().equals(Gender.FEMALE),
			UserInfoRuleType.PRODUCTION_SITE, user -> user.getPersonalInfo().getJob().equals(Job.PRODUCTION_SITE),
			UserInfoRuleType.DRIVER_DELIVERY, user -> user.getPersonalInfo().getJob().equals(DRIVER_DELIVERY),
			UserInfoRuleType.MARRIED, user -> user.getPersonalInfo().isMarried(),
			UserInfoRuleType.HAS_CHILD, user -> user.getPersonalInfo().isHasChild(),
			UserInfoRuleType.DRIVER, user -> user.getPersonalInfo().isDriver()
		);
	}

    private Map<UserInfoRuleType, Predicate<PersonalInfo>> getPersonalInfoRuleMap(int age){
        return Map.of(
            UserInfoRuleType.AT_RISK_OF_MAJOR_DISEASE,  personalInfo -> age > MAJOR_DISEASE_RISKED_AGE,
            UserInfoRuleType.FEMALE, personalInfo -> personalInfo.getGender().equals(Gender.FEMALE),
            UserInfoRuleType.PRODUCTION_SITE, personalInfo -> personalInfo.getJob().equals(Job.PRODUCTION_SITE),
            UserInfoRuleType.DRIVER_DELIVERY, personalInfo -> personalInfo.getJob().equals(DRIVER_DELIVERY),
            UserInfoRuleType.MARRIED, PersonalInfo::isMarried,
            UserInfoRuleType.HAS_CHILD, PersonalInfo::isHasChild,
            UserInfoRuleType.DRIVER, PersonalInfo::isDriver
        );
    }

	private double pointFromRule (InsuranceProduct product, UserInfoRuleType type){
		List<UserInfoScoringRule> rules = scoringRuleProvider.findAllUserInfoRuleType(type);
		return rules.stream()
			.mapToDouble(rule -> ScoringRuleUtil.getPoint(rule, product))
			.sum();
	}

}
