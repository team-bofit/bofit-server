package org.sopt.bofit.domain.insurancereport.service;

import java.util.List;
import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.service.InsuranceProductReader;
import org.sopt.bofit.domain.insurance.service.InsuranceStatisticReader;
import org.sopt.bofit.domain.insurancereport.dto.response.InsuranceReportDetailResponse;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.util.UserUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceReportService {
	private final InsuranceReportWriter insuranceReportWriter;
	private final InsuranceReportReader insuranceReportReader;

	private final InsuranceProductReader insuranceProductReader;

	private final InsuranceStatisticReader insuranceStatisticReader;

	public IssueInsuranceReportResponse recommend(User user, UserInfo userInfo){
		int age = UserUtil.convertInternationalAge(user.getBirthDate());

		List<InsuranceProduct> products
			= insuranceProductReader.getAgeAndPremiumFilteredProducts(age, userInfo.getMinPrice(), userInfo.getMaxPrice());

		InsuranceStatistic totalAverage = insuranceStatisticReader.getTotalAverage();

		InsuranceProduct recommendedProduct = insuranceReportWriter.recommendBestInsurance(
			products, user, userInfo, age);

		InsuranceReport insuranceReport = insuranceReportWriter.writeReport(
			totalAverage, recommendedProduct, user, userInfo, age);
		user.recommendedInsurance();

		return new IssueInsuranceReportResponse(insuranceReport.getId());
	}

	public InsuranceReportDetailResponse findInsuranceReportDetailById(UUID insuranceReportId){
		return InsuranceReportDetailResponse.from(
			insuranceReportReader.findByIdWithRelatedEntity(insuranceReportId));
	}
}
