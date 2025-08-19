package org.sopt.bofit.domain.insurance.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.builder.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class InsuranceProductRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private InsuranceProductRepository insuranceProductRepository;

	@BeforeEach
	void setUp(){
		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("보험 상품1")
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(40)
			.withPremium(10000)
			.build();

		InsuranceProduct product2 = new InsuranceProductTestBuilder()
			.withName("보험 상품2")
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(40)
			.withPremium(20000)
			.build();

		InsuranceProduct product3 = new InsuranceProductTestBuilder()
			.withName("보험 상품3")
			.withMinEnrollmentAge(25)
			.withMaxEnrollmentAge(100)
			.withPremium(50000)
			.build();

		InsuranceProduct product4 = new InsuranceProductTestBuilder()
			.withName("보험 상품4")
			.withMinEnrollmentAge(40)
			.withMaxEnrollmentAge(100)
			.withPremium(50000)
			.build();

		insuranceProductRepository.saveAll(List.of(product1, product2, product3, product4));
	}


	@DisplayName("해당 나이와 금액 내에 가입할 수 있는 보험 상품들만 가져옴")
	@Test
	void findAllByAgeAndPremiumTest(){
	    // given
		int age = 25;
		int minPremium = 20000;
		int maxPremium = 80000;

		// when
		List<InsuranceProduct> result = insuranceProductRepository.findAllByAgeAndPremium(age, minPremium, maxPremium);

	    // then
		assertThat(result).hasSize(2)
			.extracting("basicInformation.name",
				"basicInformation.minEnrollmentAge", "basicInformation.maxEnrollmentAge",
				"basicInformation.premium")
			.containsExactlyInAnyOrder(
				Tuple.tuple("보험 상품2", 0, 40, 20000),
						Tuple.tuple("보험 상품3", 25, 100, 50000)
			);
	}


}