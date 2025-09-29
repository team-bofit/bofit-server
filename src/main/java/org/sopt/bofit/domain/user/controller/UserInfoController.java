package org.sopt.bofit.domain.user.controller;

import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_DESCRIPTION_USER_INFO;
import static org.sopt.bofit.global.constant.SwaggerConstant.TAG_NAME_USER_INFO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurance.dto.response.InsuranceOptionsResponse;
import org.sopt.bofit.domain.insurance.service.InsuranceService;
import org.sopt.bofit.domain.user.dto.response.CoveragePreferenceResponses;
import org.sopt.bofit.domain.user.dto.response.DiagnosedDiseaseResponses;
import org.sopt.bofit.domain.user.dto.response.JobResponses;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-infos")
public class UserInfoController {

	private final UserService userService;
    private final InsuranceService insuranceService;

	@Tag(name = TAG_NAME_USER_INFO, description = TAG_DESCRIPTION_USER_INFO)
	@Operation(summary = "직업 목록 조회", description = "선택 가능한 직업 목록을 조회합니다.")
	@GetMapping("/jobs")
	public BaseResponse<JobResponses> getJobs() {
		return BaseResponse.ok(userService.getJobs(), "선택 가능한 직업 목록 조회 성공");
	}

	@Tag(name = TAG_NAME_USER_INFO, description = TAG_DESCRIPTION_USER_INFO)
	@Operation(summary = "진단 받은 질병 목록 조회", description = "선택 가능한 진단 받았던 질병 목록을 조회합니다.")
	@GetMapping("/diagnosed-disease")
	public BaseResponse<DiagnosedDiseaseResponses> getDiagnosedDisease() {
		return BaseResponse.ok(userService.getDiagnosedDiseaseNames(), "진단 받은 질병 목록 조회 성공");
	}

	@Tag(name = TAG_NAME_USER_INFO, description = TAG_DESCRIPTION_USER_INFO)
	@Operation(summary = "보장 상황 목록 조회", description = "선택 가능한 보장 상황 목록을 조회합니다.")
	@GetMapping("/coverage-select")
	public BaseResponse<CoveragePreferenceResponses> getCoverageSelect() {
		CoveragePreferenceResponses response = userService.getCoveragePreference();
		return BaseResponse.ok(response, "보장 상황 목록 조회 성공");
	}

    @Tag(name = TAG_NAME_USER_INFO, description = TAG_DESCRIPTION_USER_INFO)
    @Operation(summary = "보험 추천 시 선택 사항 항목 조회", description = "보험 추천 시 보험 상품의 선택 사항 항목을 조회합니다.")
    @GetMapping("/insurances/options")
    public BaseResponse<InsuranceOptionsResponse> getInsurancesOptions() {
        InsuranceOptionsResponse response = insuranceService.getOptionInfos();
        return BaseResponse.ok(response, "보험 추천 시 선택 사항 항목 조회");
    }

}
