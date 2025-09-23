package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sopt.bofit.domain.insurancereport.constant.ScoringRuleConstant.MAJOR_DISEASE_RISKED_AGE;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.ATYPICAL_CANCER_DIAGNOSIS;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.GENERAL_CANCER_DIAGNOSIS;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.INJURY_SURGERY;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.ISCHEMIC_HEART_DISEASE_DIAGNOSIS;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator.EXIST;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator.GE;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType.AT_RISK_OF_MAJOR_DISEASE;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType.DRIVER;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType.FEMALE;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType.HAS_CHILD;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoScoringRule;
import org.sopt.bofit.domain.insurancereport.fixture.UserFixture;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.UserInfoScoringRuleRepository;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class UserInfoRuleCalculatorTest extends IntegrationTestSupport {
	@Autowired
	private UserInfoRuleCalculator userInfoRuleCalculator;

	@Autowired
	private UserInfoScoringRuleRepository userInfoScoringRuleRepository;

	@AfterEach
	void clearInAfterTest(){
		userInfoScoringRuleRepository .deleteAllInBatch();
	}

	@DisplayName("유저 정보와 관련된 ScoringRule 점수를 정상적으로 계산한다.")
	@Test
	void calculateTest(){
		// given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withGeneralCancerDiagnosis(5000)
			.withAtypicalCancerDiagnosis(1000)
			.withInjurySurgery(1000)
			.withDailyHospitalizationDisease(50)
			.withIschemicDiagnosis(1000)
			.build();

        PersonalInfo personalInfo = PersonalInfo.builder()
            .job(Job.DRIVER_DELIVERY)
            .isDriver(true)
            .gender(Gender.FEMALE)
            .build();

		User user = UserFixture.getUser(personalInfo);

		UserInfoScoringRule appliedRule1 = UserInfoScoringRule.create(
			FEMALE,
			ATYPICAL_CANCER_DIAGNOSIS,
			EXIST,
			0,
			5d
		);

		UserInfoScoringRule appliedRule2 = UserInfoScoringRule.create(
			AT_RISK_OF_MAJOR_DISEASE,
			GENERAL_CANCER_DIAGNOSIS,
			GE,
			5000,
			10d
		);

		UserInfoScoringRule appliedRule3 = UserInfoScoringRule.create(
			DRIVER,
			INJURY_SURGERY,
			GE,
			1000,
			3d
		);

		UserInfoScoringRule noneAppliedRule1 = UserInfoScoringRule.create(
			HAS_CHILD,
			ISCHEMIC_HEART_DISEASE_DIAGNOSIS,
			ConditionOperator.GE,
			3000,
			10d
		);

		userInfoScoringRuleRepository.saveAll(List.of(
			appliedRule1,
			appliedRule2,
			appliedRule3,
			noneAppliedRule1
		));

		List<UserInfoRuleType> userInfoRuleTypes = List.of(
			FEMALE,
			AT_RISK_OF_MAJOR_DISEASE,
			HAS_CHILD,
			DRIVER
		);

		// when
		double result = userInfoRuleCalculator.calculate(user, product, MAJOR_DISEASE_RISKED_AGE + 1);

		// then
		assertThat(result).isEqualTo(
			appliedRule1.getPoint() +
			appliedRule2.getPoint() +
			appliedRule3.getPoint()
		);
	}
}