package org.sopt.bofit.domain.insurance.controller;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

import java.util.UUID;

import org.sopt.bofit.domain.insurancereport.dto.request.InsuranceReportRequest;
import org.sopt.bofit.domain.insurancereport.dto.response.InsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.dto.response.dailyHospitalization.DailyHospitalizationSection;
import org.sopt.bofit.domain.insurancereport.dto.response.death.DeathSection;
import org.sopt.bofit.domain.insurancereport.dto.response.disability.DisabilitySection;
import org.sopt.bofit.domain.insurancereport.dto.response.majordisease.MajorDiseaseSection;
import org.sopt.bofit.domain.insurancereport.dto.response.surgery.SurgerySection;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@Operation(summary = "보험 추천 리포트 조회", description = "보험 추천 리포트를 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}")
	public BaseResponse<InsuranceReportResponse > getReport(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){
		InsuranceReportResponse response =
			insuranceReportService.findReportResponse(insuranceReportId);
		return BaseResponse.ok(response, "보험 추천 리포트 조회 성공");
	}

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(GET_INSURANCE_REPORT)
	@Operation(summary = "보험 추천 리포트 큰병 섹션 조회", description = "보험 추천 리포트 큰병 섹션 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}/major-disease")
	public BaseResponse<MajorDiseaseSection > getReportMajorSection(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@RequestParam(name = "section") String section,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){

		MajorDiseaseSection response =
			insuranceReportService.findMajorDiseaseSection(insuranceReportId, section);
		return BaseResponse.ok(response, "보험 추천 리포트 큰병 섹션 조회 성공");
	}

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(GET_INSURANCE_REPORT)
	@Operation(summary = "보험 추천 리포트 수술 섹션 조회", description = "보험 추천 리포트 수술 섹션을 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}/surgery")
	public BaseResponse<SurgerySection> getReportSurgerySection(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@RequestParam(name = "section") String section,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){

		SurgerySection response = insuranceReportService.findSurgerySection(insuranceReportId, section);

		return BaseResponse.ok(response, "보험 추천 리포트 수술 섹션 조회 성공");
	}

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(GET_INSURANCE_REPORT)
	@Operation(summary = "보험 추천 리포트 일당입원비 상세 조회", description = "보험 추천 리포트 일당입원비를 상세 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}/hospitalization")
	public BaseResponse<DailyHospitalizationSection> getHospitalizationSection(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@RequestParam(name = "section") String section,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){

		DailyHospitalizationSection response = insuranceReportService.findHospitalizationSection(
			insuranceReportId, section);
		return BaseResponse.ok(response, "보험 추천 리포트 일당입원비 섹션 조회 성공");
	}

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(GET_INSURANCE_REPORT)
	@Operation(summary = "보험 추천 리포트 장해 섹션 조회", description = "보험 추천 리포트 장해 섹션을 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}/disability")
	public BaseResponse<DisabilitySection> getDisabilitySection(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@RequestParam(name = "section") String section,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){

		DisabilitySection response = insuranceReportService.findDisabilitySection(
			insuranceReportId, section);
		return BaseResponse.ok(response, "보험 추천 리포트 장해 섹션 조회 성공");
	}

	@Tag(name = "Insurance", description = "보험 관련 API")
	@CustomExceptionDescription(GET_INSURANCE_REPORT)
	@Operation(summary = "보험 추천 리포트 사망 섹션 조회", description = "보험 추천 리포트 사망 섹션을 조회합니다.")
	@GetMapping("/reports/{insurance-report-id}/death")
	public BaseResponse<DeathSection> getDeathSection(
		@Parameter(hidden = true) @LoginUserId Long userId,
		@RequestParam(name = "section") String section,
		@PathVariable(name = "insurance-report-id") UUID insuranceReportId){

		DeathSection response = insuranceReportService.findDeathSection(insuranceReportId, section);
		return BaseResponse.ok(response, "보험 추천 리포트 사망 섹션 조회 성공");
	}
}
