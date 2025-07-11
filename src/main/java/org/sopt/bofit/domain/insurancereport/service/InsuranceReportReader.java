package org.sopt.bofit.domain.insurancereport.service;

import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.global.exception.constant.InsuranceErrorCode;
import org.sopt.bofit.global.exception.custom_exception.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceReportReader {
	private final InsuranceReportRepository insuranceReportRepository;

	public InsuranceReport findById(UUID insuranceReportId){
		return insuranceReportRepository.findById(insuranceReportId).orElseThrow(() ->
			new NotFoundException(InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT));
	}

	public InsuranceReport findByIdWithRelatedEntity(UUID insuranceReportId){
		return insuranceReportRepository.findById(insuranceReportId).orElseThrow(() ->
			new NotFoundException(InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT));
	}
}
