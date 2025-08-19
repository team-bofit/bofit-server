package org.sopt.bofit.domain.insurancereport.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

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
import org.sopt.bofit.domain.insurance.repository.InsuranceProductRepository;
import org.sopt.bofit.domain.insurance.repository.InsuranceStatisticRepository;
import org.sopt.bofit.domain.insurancereport.builder.InsuranceReportTestBuilder;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.dto.response.majordisease.MajorDiseaseSection;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.repository.UserInfoRepository;
import org.sopt.bofit.domain.user.repository.UserRepository;
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
		User user = User.builder()
			.name("유저1")
			.birthDate(birth)
			.loginProvider(LoginProvider.KAKAO)
			.gender(Gender.FEMALE)
			.job(Job.STUDENT)
			.isMarried(false)
			.hasChild(false)
			.oauthId("0123456")
			.build();

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

		List<InsuranceProduct> insuranceProducts = insuranceProductRepository.saveAll(List.of(product1, product2, product3));
		insuranceStatisticRepository.save(statistic);
		User savedUser = userRepository.save(user);

		when(openAiClient.sendReportRelationalRequest(anyList()))
			.thenReturn(new ReportRationale(DEFAULT_RATIONALE_REASONS, DEFAULT_RATIONAL_KEYWORD_CHIPS));

		// when
		IssueInsuranceReportResponse result = insuranceReportService.recommend(user, userInfo);

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

		User user = User.builder()
			.name("유저1")
			.loginProvider(LoginProvider.KAKAO)
			.oauthId("0123456")
			.build();

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

		User user = User.builder()
			.name("유저1")
			.loginProvider(LoginProvider.KAKAO)
			.oauthId("0123456")
			.build();

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