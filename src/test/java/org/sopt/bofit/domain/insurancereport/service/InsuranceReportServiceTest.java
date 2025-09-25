package org.sopt.bofit.domain.insurancereport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.DEFAULT_RATIONALE_REASONS;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.DEFAULT_RATIONAL_KEYWORD_CHIPS;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.INVALID_REPORT_SECTION;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.builder.InsuranceStatisticTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.entity.statistic.StatisticRange;
import org.sopt.bofit.domain.insurance.fixture.InsuranceFixture;
import org.sopt.bofit.domain.insurance.repository.InsuranceProductRepository;
import org.sopt.bofit.domain.insurance.repository.InsuranceStatisticRepository;
import org.sopt.bofit.domain.insurancereport.builder.InsuranceReportTestBuilder;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.dto.response.majordisease.MajorDiseaseSection;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.domain.insurancereport.fixture.UserFixture;
import org.sopt.bofit.domain.insurancereport.fixture.UserInfoFixture;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.repository.UserInfoRepository;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.domain.user.service.dto.request.UserInfoCommand;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class InsuranceReportServiceTest extends IntegrationTestSupport {

	@Autowired
	private InsuranceReportService insuranceReportService;

	@Autowired
	private InsuranceReportReader insuranceReportReader;

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

	@DisplayName("리포트를 정상적으로 생성함")
	@Test
	void createReport(){
		// given
		String productName = "테스트용 보험 상품";
		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품1")
			.withPremium(1000)
			.withMinEnrollmentAge(10)
			.withMaxEnrollmentAge(100)
			.build();

		InsuranceProduct product2 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품2")
			.withPremium(20000)
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(20)
			.build();

		InsuranceProduct product3 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품3")
			.withPremium(20000)
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(100)
			.build();

		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.build();

		LocalDate birth = LocalDate.now().minusYears(30);
		User user = UserFixture.getUser();

		Map<CoveragePreference, Integer> selectedCoverages = Map.of(
			CoveragePreference.MAXIMUM_COVERAGE, 1,
			CoveragePreference.MAJOR_DISEASE, 2
		);

        UserInfoCommand userInfo = UserInfoFixture.userInfoCommand(
            10000,
            100000,
            List.of(DiagnosedDisease.NONE),
            List.of(DiagnosedDisease.NONE),
            selectedCoverages
        );

		List<InsuranceProduct> insuranceProducts = insuranceProductRepository.saveAll(List.of(product1, product2, product3));
		insuranceStatisticRepository.save(statistic);
		User savedUser = userRepository.save(user);

		when(openAiClient.sendReportRelationalRequest(anyList()))
			.thenReturn(new ReportRationale(DEFAULT_RATIONALE_REASONS, DEFAULT_RATIONAL_KEYWORD_CHIPS));

		// when
		IssueInsuranceReportResponse result = insuranceReportService.recommend(
            savedUser.getId(), userInfo, UserFixture.getPersonalInfo(), InsuranceFixture.getEmptyInsuranceOptionCommand());

		// then
		Optional<InsuranceReport> resultReportId = insuranceReportRepository.findById(result.insuranceReportId());
		InsuranceReport resultReport = insuranceReportReader.findByIdWithProductAndStatistic(
			resultReportId.get().getId());

		assertThat(resultReport.getProduct().getBasicInformation().getName())
			.isEqualTo("테스트용 보험 상품3");

		assertThat(resultReport)
			.extracting("user.id", "reportRationale.reasons")
			.containsExactly(savedUser.getId(), DEFAULT_RATIONALE_REASONS);

		assertThat(userInfoRepository.findAll().size())
			.isEqualTo(1);

        User u2 = userRepository.findById(savedUser.getId()).get();
        assertTrue(u2.isRecommendInsurance());
	}

	@DisplayName("주요 질병 섹션 조회 시 쿼리파라미터에 section=cancer 인 경우 정상적으로 조회함")
	@Test
	    // given
		void getMajorDiseaseSection(){

		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품1")
			.withGeneralCancerDiagnosis(5000)
			.withGeneralCancerSurgery(1000)
			.build();

		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.withGeneralCancerDiagnosis(4000)
			.withGeneralCancerSurgery(500)
			.build();

		User user = UserFixture.getUser();

		insuranceProductRepository.save(product1);
		insuranceStatisticRepository.save(statistic);
		userRepository.save(user);

		InsuranceReport report1 = new InsuranceReportTestBuilder()
			.withMajorDisease(CoverageStatus.POWERFUL)
			.withCancer(CoverageStatus.POWERFUL)
			.withProduct(product1)
			.withStatistic(statistic)
			.withUser(user)
			.build();

		InsuranceReport savedReport = insuranceReportRepository.save(report1);

	    // when
		MajorDiseaseSection result = insuranceReportService.findMajorDiseaseSection(savedReport.getId(), "cancer");

		// then
		assertThat(result.sections())
			.extracting("displayName", "diagnosis.productCoverage", "diagnosis.averageCoverage")
			.contains(
				Tuple.tuple(Disease.GENERAL_CANCER.getDisplayName(), 5000, 4000)
			);
	}

	@DisplayName("주요 질병 섹션 조회 시 잘못된 키워드가 들어온 경우 예외가 발생함.")
	@Test
	void getMajorDiseaseSectionWhenWrongKeyword(){
		// given

		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품1")
			.withGeneralCancerDiagnosis(5000)
			.withGeneralCancerSurgery(1000)
			.build();

		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.withGeneralCancerDiagnosis(4000)
			.withGeneralCancerSurgery(500)
			.build();

		User user = UserFixture.getUser();

		insuranceProductRepository.save(product1);
		insuranceStatisticRepository.save(statistic);
		userRepository.save(user);

		InsuranceReport report1 = new InsuranceReportTestBuilder()
			.withMajorDisease(CoverageStatus.POWERFUL)
			.withCancer(CoverageStatus.POWERFUL)
			.withProduct(product1)
			.withStatistic(statistic)
			.withUser(user)
			.build();

		InsuranceReport savedReport = insuranceReportRepository.save(report1);

		// when // then
		assertThatThrownBy(() -> insuranceReportService.findMajorDiseaseSection(savedReport.getId(), "cancer2"))
			.isInstanceOf(BadRequestException.class)
				.satisfies(e->{
					BadRequestException exception = (BadRequestException)e;
					assertThat(exception.getErrorCode()).isEqualTo(INVALID_REPORT_SECTION);
				});
	}

}