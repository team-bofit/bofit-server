package org.sopt.bofit.domain.insurance.controller;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

import java.util.UUID;

import org.sopt.bofit.domain.insurancereport.dto.request.InsuranceReportRequest;
import org.sopt.bofit.domain.insurancereport.dto.response.InsuranceReportDetailResponse;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insurances")
public class InsuranceController {

	private final InsuranceReportService insuranceReportService;
	private final UserService userService;

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(ISSUE_INSURANCE_REPORT)
	@Operation(summary = "보험 상품 추천", description = "보험 상품을 추천 받습니다.")
	@PostMapping("/reports")
	public BaseResponse<IssueInsuranceReportResponse> issueReport(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@Valid @RequestBody InsuranceReportRequest request){
		User user = userService.userUpdate(userId, request.toUserUpdate());
		IssueInsuranceReportResponse response = insuranceReportService.recommend(user, request.toUserInfo(user));
		return BaseResponse.ok(response, "보험 추천 리포트 발급 성공");
	}

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(GET_INSURANCE_REPORT)
	@Operation(summary = "보험 상품 상세 조회", description = "보험 상품을 상세 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}")
	public BaseResponse<InsuranceReportDetailResponse> getReport(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){
		InsuranceReportDetailResponse response =
			insuranceReportService.findInsuranceReportDetailById(insuranceReportId);
		return BaseResponse.ok(response, "보험 추천 리포트 상세 조회 성공");
	}

}
