package org.sopt.bofit.domain.insurance.controller;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

import org.sopt.bofit.domain.insurancereport.dto.request.InsuranceReportRequest;
import org.sopt.bofit.domain.insurancereport.dto.response.InsuranceReportDetailResponse;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.domain.user.dto.response.UserProfileResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.response.BaseResponse;
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
	@CustomExceptionDescription(INSURANCE_REPORT)
	@Operation(summary = "보험 상품 추천", description = "보험 상품을 추천 받습니다.")
	@PostMapping("/reports")
	public BaseResponse<InsuranceReportDetailResponse> report(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@Valid @RequestBody InsuranceReportRequest request){
		User user = userService.userUpdate(userId, request.toUserUpdate());

		InsuranceReportDetailResponse response = insuranceReportService.recommend(user, request.toUserInfo(user));
		return BaseResponse.ok(response, "보험 추천 리포트 발급 성공");
	}

}
