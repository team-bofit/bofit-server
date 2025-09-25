package org.sopt.bofit.domain.insurancereport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.DEFAULT_RATIONALE_REASONS;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.DEFAULT_RATIONAL_KEYWORD_CHIPS;
import static org.sopt.bofit.domain.insurancereport.fixture.UserFixture.PERSONAL_NAME;
import static org.sopt.bofit.domain.insurancereport.fixture.UserFixture.getPersonalInfo;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.builder.InsuranceStatisticTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.entity.statistic.StatisticRange;
import org.sopt.bofit.domain.insurance.repository.InsuranceProductRepository;
import org.sopt.bofit.domain.insurance.repository.InsuranceStatisticRepository;
import org.sopt.bofit.domain.insurancereport.builder.InsuranceReportTestBuilder;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.domain.insurancereport.fixture.UserFixture;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.repository.UserInfoRepository;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class InsuranceReportWriterTest extends IntegrationTestSupport {

	@Autowired
	private InsuranceReportWriter insuranceReportWriter;

	@Autowired
	private InsuranceReportRepository insuranceReportRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InsuranceProductRepository insuranceProductRepository;

	@Autowired
	private InsuranceStatisticRepository insuranceStatisticRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@AfterEach
	void clearInAfterTest(){
		userInfoRepository.deleteAllInBatch();
		insuranceReportRepository.deleteAllInBatch();
		insuranceProductRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
		insuranceStatisticRepository.deleteAllInBatch();
	}

	@DisplayName("유저 요청 범위에 해당하는 상품이 존재하지 않는 경우 추천 상품 중 하나를 반환함")
	@Test
	void recommendWhenProductsNotExist(){
	    // given
		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("테스트용 상품1")
			.withStatus(InsuranceStatus.SELLING)
			.withPremium(200000)
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(100)
			.build();

		InsuranceProduct product2 = new InsuranceProductTestBuilder()
			.withName("테스트용 상품1")
			.withStatus(InsuranceStatus.SELLING)
			.withPremium(15000)
			.withMinEnrollmentAge(30)
			.withMaxEnrollmentAge(100)
			.build();

		InsuranceProduct recommendedProduct1 = new InsuranceProductTestBuilder()
			.withName("추천 상품1")
			.withPremium(200000)
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(100)
			.withStatus(InsuranceStatus.RECOMMENDED)
			.build();

		UserInfo userInfo = UserInfo.builder()
			.minPrice(10000)
			.maxPrice(20000)
			.build();

		insuranceProductRepository.saveAll(List.of(product1, product2, recommendedProduct1));

	    // when
		InsuranceProduct result = insuranceReportWriter.recommendBestInsurance(List.of(), getPersonalInfo() , userInfo, 10);

		// then
		assertThat(result)
			.extracting("status", "basicInformation.name")
			.containsExactly(InsuranceStatus.RECOMMENDED, "추천 상품1");
	}

    @DisplayName("리포트를 정상적으로 생성해서 반환함.")
    @Test
    void createReport(){
        // given
        String productName = "테스트용 보험 상품";
        InsuranceProduct product = new InsuranceProductTestBuilder()
            .withName(productName)
            .build();

        InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
            .withStatisticRange(StatisticRange.TOTAL_AVERAGE)
            .build();

        User user = UserFixture.getUser();
        Map<CoveragePreference, Integer> selectedCoverages = Map.of(
            CoveragePreference.MAXIMUM_COVERAGE, 1,
            CoveragePreference.MAJOR_DISEASE, 2
        );

        UserInfo userInfo = UserInfo.builder()
            .minPrice(10000)
            .maxPrice(100000)
            .familyHistory(List.of(DiagnosedDisease.NONE))
            .diseaseHistory(List.of(DiagnosedDisease.NONE))
            .coveragePreferences(selectedCoverages)
            .user(user)
            .build();

        InsuranceProduct savedProduct = insuranceProductRepository.save(product);
        InsuranceStatistic savedStatistic = insuranceStatisticRepository.save(statistic);
        User savedUser = userRepository.save(user);

        when(openAiClient.sendReportRelationalRequest(anyList()))
            .thenReturn(new ReportRationale(DEFAULT_RATIONALE_REASONS, DEFAULT_RATIONAL_KEYWORD_CHIPS));

        PersonalInfo personalInfo = UserFixture.getPersonalInfo();

        // when
        InsuranceReport result =
            insuranceReportWriter.createReport(statistic, savedProduct, savedUser, userInfo, personalInfo, 30);

        // then
        assertThat(result)
            .isNotNull()
            .extracting("product.basicInformation.name", "user.personalInfo.name", "reportRationale.reasons")
            .containsExactly(productName, PERSONAL_NAME, DEFAULT_RATIONALE_REASONS );

    }

	@DisplayName("리포트를 정상적으로 저장하고 유저의 리포트 발급 상태, 유저 입력 데이터 등이 정상적으로 저장됨")
	@Test
	void saveReport(){
	    // given
		String productName = "테스트용 보험 상품";
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withName(productName)
			.build();

		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.build();

		User user = UserFixture.getUser();

		Map<CoveragePreference, Integer> selectedCoverages = Map.of(
			CoveragePreference.MAXIMUM_COVERAGE, 1,
			CoveragePreference.MAJOR_DISEASE, 2
		);

		UserInfo userInfo = UserInfo.builder()
			.minPrice(10000)
			.maxPrice(100000)
			.familyHistory(List.of(DiagnosedDisease.NONE))
			.diseaseHistory(List.of(DiagnosedDisease.NONE))
			.coveragePreferences(selectedCoverages)
			.user(user)
			.build();

		InsuranceProduct savedProduct = insuranceProductRepository.save(product);
		InsuranceStatistic savedStatistic = insuranceStatisticRepository.save(statistic);
		User savedUser = userRepository.save(user);

        InsuranceReport insuranceReport = new InsuranceReportTestBuilder()
            .withUser(savedUser)
            .withProduct(savedProduct)
            .withStatistic(savedStatistic)
            .build();

        PersonalInfo personalInfo = UserFixture.getPersonalInfo();

	    // when
		InsuranceReport result = insuranceReportWriter.saveReport(insuranceReport, savedUser, userInfo, personalInfo);

		// then
		assertThat(userInfoRepository.findAll().size()).isEqualTo(1);
        assertThat(insuranceReportRepository.findAll().size()).isEqualTo(1);

        User resultUser = userRepository.findById(savedUser.getId()).get();
        assertTrue(resultUser.isRecommendInsurance());
    }

}