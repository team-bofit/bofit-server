package org.sopt.bofit.domain.insurancereport.service;

import java.util.UUID;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
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
		return insuranceReportRepository.findByIdWithProductAndStatistic(insuranceReportId).orElseThrow(() ->
			new NotFoundException(InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT));
	}

	public InsuranceReport findLastByUser(User user){
		return insuranceReportRepository.findFirstByUserOrderByCreatedAtDesc(user).orElseThrow(()->
			new NotFoundException(InsuranceReportErrorCode.NOT_FOUND_INSURANCE_REPORT));
	}
}
