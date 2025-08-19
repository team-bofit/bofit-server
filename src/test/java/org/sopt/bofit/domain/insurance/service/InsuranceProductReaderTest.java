package org.sopt.bofit.domain.insurance.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurance.InsuranceProductTestBuilder;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;
import org.sopt.bofit.domain.insurance.repository.InsuranceProductRepository;
import org.sopt.bofit.global.exception.constant.InsuranceErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.sopt.bofit.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;

class InsuranceProductReaderTest extends IntegrationTestSupport {

	@Autowired
	private InsuranceProductReader insuranceProductReader;

	@Autowired
	private InsuranceProductRepository insuranceProductRepository;

	@AfterEach
	void clearInAfterTest(){
		insuranceProductRepository.deleteAllInBatch();
	}

	@DisplayName("요청자의 나이와 요구 금액에 일치하는 상품들만 반환함")
	@Test
	void ageAndPremiumFilteredTest(){
	    // given
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

	    // when
		int age = 25;
		int minPremium = 20000;
		int maxPremium = 80000;

		List<InsuranceProduct> result = insuranceProductReader.getAgeAndPremiumFilteredProducts(
			age, minPremium, maxPremium);

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

	@DisplayName("추천 상태인 상품이 존재하는 경우 정상적으로 추천 상태인 상품을 반환함")
	@Test
	void getRecommendedStatusProductWhenExist(){
		// given
		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("보험 상품1")
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(40)
			.withPremium(10000)
			.build();

		String recommendedProductName = "추천 보험 상품";
		InsuranceProduct product2 = new InsuranceProductTestBuilder()
			.withName(recommendedProductName)
			.withStatus(InsuranceStatus.RECOMMENDED)
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

		insuranceProductRepository.saveAll(List.of(product1, product2, product3));

		// when
		InsuranceProduct result = insuranceProductReader.getRecommendedStatusProducts();

		// then
		assertThat(result)
			.extracting("basicInformation.name", "status")
			.containsExactly(recommendedProductName, InsuranceStatus.RECOMMENDED);
	}

	@DisplayName("추천 상태인 상품이 2개 이상인 경우 그 중 하나인 상품을 반환함")
	@Test
	void getRecommendedStatusProductWhenTwoExist(){
		// given
		InsuranceProduct product1 = new InsuranceProductTestBuilder()
			.withName("보험 상품1")
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(40)
			.withPremium(10000)
			.build();

		String recommendedProductName1 = "추천 보험 상품1";
		InsuranceProduct recommendProduct1 = new InsuranceProductTestBuilder()
			.withName(recommendedProductName1)
			.withStatus(InsuranceStatus.RECOMMENDED)
			.withMinEnrollmentAge(0)
			.withMaxEnrollmentAge(40)
			.withPremium(20000)
			.build();

		String recommendedProductName2 = "추천 보험 상품2";
		InsuranceProduct recommendProduct2 = new InsuranceProductTestBuilder()
			.withName(recommendedProductName2)
			.withMinEnrollmentAge(25)
			.withMaxEnrollmentAge(100)
			.withPremium(50000)
			.build();

		insuranceProductRepository.saveAll(List.of(product1, recommendProduct1, recommendProduct2));
		List<String> recommendProductNames = List.of(recommendedProductName1, recommendedProductName2);

		// when
		InsuranceProduct result = insuranceProductReader.getRecommendedStatusProducts();

		// then
		assertThat(result.getBasicInformation().getName())
			.isNotNull()
			.isIn(recommendProductNames);
	}

	@DisplayName("추천 상태인 상품이 존재하지 않는 경우 예외 발생")
	@Test
	void getRecommendedStatusProductWhenNotExist(){
		// given
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

		insuranceProductRepository.saveAll(List.of(product1, product2, product3));

		// when & then
		assertThatThrownBy(() -> insuranceProductReader.getRecommendedStatusProducts())
			.isInstanceOf(InternalException.class)
			.satisfies(e ->{
				InternalException exception = (InternalException)e;
				assertThat(exception.getErrorCode()).isEqualTo(InsuranceErrorCode.NOT_FOUND_RECOMMENDED_STATUS_INSURANCE);
			});
	}

}