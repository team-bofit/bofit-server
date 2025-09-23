package org.sopt.bofit.domain.insurancereport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
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
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode;
import org.sopt.bofit.domain.insurancereport.fixture.UserFixture;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.repository.UserRepository;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class InsuranceReportReaderTest extends IntegrationTestSupport {

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

	@PersistenceContext
	private EntityManager entityManager;

	@AfterEach
	void clearInAfterTest(){
		insuranceReportRepository.deleteAllInBatch();
		insuranceProductRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
		insuranceStatisticRepository.deleteAllInBatch();
	}

	@DisplayName("연관 엔티티가 모두 로딩된 객체를 정상적으로 반환함")
	@Test
	void initializedProductAndStatistic(){
	    // given
		InsuranceProduct product = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품")
			.build();

		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.build();

		User user = UserFixture.getUser();

		insuranceProductRepository.save(product);
		insuranceStatisticRepository.save(statistic);
		userRepository.save(user);

		InsuranceReport report = new InsuranceReportTestBuilder()
			.withProduct(product)
			.withStatistic(statistic)
			.withUser(user)
			.build();

		InsuranceReport savedReport = insuranceReportRepository.save(report);
		UUID reportId = savedReport.getId();

		// when
		InsuranceReport result = insuranceReportReader.findByIdWithProductAndStatistic(reportId);

	    // then
		assertTrue(entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(result.getProduct()));
		assertTrue(entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(result.getStatistic()));
		assertFalse(entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(result.getUser()));
	}

	@DisplayName("존재하지 않는 리포트를 조회하는 경우 예외가 발생함")
	@Test
	void findByIdWhenReportDidntExist(){
		// given
		UUID notExistId = UUID.randomUUID();

		// when // then
		assertThatThrownBy(() -> insuranceReportReader.findByIdWithProductAndStatistic(notExistId))
			.isInstanceOf(NotFoundException.class)
			.satisfies(e->{
				NotFoundException exception = (NotFoundException) e;
				assertThat(exception.getErrorCode()).isEqualTo(InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT);
			});
	}

	/**
	 * 현재는 CreatedAt을 외부에서 주입받는 형태가 아니므로 직접 createdAt을 비교함
	 */
	@DisplayName("유저의 최신 리포트를 정상적으로 반환함")
	@Test
	void getLastReportOfUser(){
		// given
		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품1")
			.build();

		InsuranceProduct product2 = new InsuranceProductTestBuilder()
			.withName("테스트용 보험 상품2")
			.build();

		InsuranceStatistic statistic = new InsuranceStatisticTestBuilder()
			.withStatisticRange(StatisticRange.TOTAL_AVERAGE)
			.build();

		User user = UserFixture.getUser();

		insuranceProductRepository.saveAll(List.of(product1, product2));
		insuranceStatisticRepository.save(statistic);
		userRepository.save(user);

		InsuranceReport report1 = new InsuranceReportTestBuilder()
			.withMajorDisease(CoverageStatus.POWERFUL)
			.withProduct(product1)
			.withStatistic(statistic)
			.withUser(user)
			.build();
		InsuranceReport savedReport1 = insuranceReportRepository.save(report1);

		InsuranceReport report2 = new InsuranceReportTestBuilder()
			.withMajorDisease(CoverageStatus.WEAKNESS)
			.withProduct(product2)
			.withStatistic(statistic)
			.withUser(user)
			.build();
		InsuranceReport savedReport2 = insuranceReportRepository.save(report2);

		// when
		InsuranceReport result = insuranceReportReader.findLastByUser(user);

		// then
		if(savedReport2.getCreatedAt().isAfter(savedReport1.getCreatedAt())){
			assertThat(result.getMajorDisease())
				.isEqualTo(savedReport2.getMajorDisease());
		}else {
			assertThat(result.getMajorDisease())
				.isEqualTo(savedReport1.getMajorDisease());
		}
	}

	@DisplayName("유저의 최신 리포트가 존재하지 않는 경우 예외가 발생함")
	@Test
	void getLastReportWhenReportDidntExist(){
		// given
		User user = UserFixture.getUser();
		userRepository.save(user);

		// when // then
		assertThatThrownBy(() -> insuranceReportReader.findLastByUser(user))
			.isInstanceOf(NotFoundException.class)
			.satisfies(e->{
				NotFoundException exception = (NotFoundException) e;
				assertThat(exception.getErrorCode())
					.isEqualTo(InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT);
			});
	}

}