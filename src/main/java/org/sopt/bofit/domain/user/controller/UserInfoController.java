package org.sopt.bofit.domain.user.controller;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.*;

import org.sopt.bofit.domain.user.dto.response.CoveragePreferenceResponses;
import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.dto.response.MyPostSummaryResponse;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.dto.response.SliceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-info")
public class UserInfoController {

	private final UserService userService;

	@Tag(name = "UserInfo", description = "유저 정보 관련 API")
	@Operation(summary = "직업 목록 조회", description = "선택 가능한 직업 목록을 조회합니다.")
	@GetMapping("/jobs")
	public BaseResponse<JobResponses> getJobs() {
		return BaseResponse.ok(userService.getJobs(), "선택 가능한 직업 목록 조회 성공");
	}

	@Tag(name = "UserInfo", description = "유저 정보 관련 API")
	@Operation(summary = "진단 받은 질병 목록 조회", description = "선택 가능한 진단 받았던 질병 목록을 조회합니다.")
	@GetMapping("/diagnosed-disease")
	public BaseResponse<DiagnosedDiseaseResponses> getDiagnosedDisease() {
		return BaseResponse.ok(userService.getDiagnosedDiseaseNames(), "진단 받은 질병 목록 조회 성공");
	}

	@Tag(name = "UserInfo", description = "유저 정보 관련 API")
	@Operation(summary = "보장 상황 목록 조회", description = "선택 가능한 보장 상황 목록을 조회합니다.")
	@GetMapping("/coverage-select")
	public BaseResponse<CoveragePreferenceResponses> getCoverageSelect() {
		CoveragePreferenceResponses response = userService.getCoveragePreference();
		return BaseResponse.ok(response, "보장 상황 목록 조회 성공");
	}


}
