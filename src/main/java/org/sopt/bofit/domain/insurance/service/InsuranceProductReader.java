package org.sopt.bofit.domain.insurance.service;

import java.util.List;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;
import org.sopt.bofit.domain.insurance.repository.InsuranceProductRepository;
import org.sopt.bofit.global.exception.constant.InsuranceErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceProductReader {

	private final InsuranceProductRepository insuranceProductRepository;


	public List<InsuranceProduct> getAgeAndPremiumFilteredProducts(
		int age,
		int minPremium,
		int maxPremium
	){
		return insuranceProductRepository.findAllByAgeAndPremium(age, minPremium, maxPremium);
	}

	public InsuranceProduct getRecommendedStatusProducts(){
		return insuranceProductRepository.findFirstByStatus(InsuranceStatus.RECOMMENDED).orElseThrow(() ->
				new InternalException(InsuranceErrorCode.NOT_FOUND_RECOMMENDED_STATUS_INSURANCE));
	}

}
