package org.sopt.bofit.domain.insurancereport.service;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceReportReader {
	private final InsuranceReportRepository insuranceReportRepository;
}
