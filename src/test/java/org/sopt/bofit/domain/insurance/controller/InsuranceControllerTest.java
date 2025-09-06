package org.sopt.bofit.domain.insurance.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurancereport.dto.request.InsuranceReportRequest;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.support.ControllerTestSupport;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.PREMIUM_RANGE;
import static org.sopt.bofit.domain.user.entity.constant.CoveragePreference.*;
import static org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease.CANCER;
import static org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease.RIVER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InsuranceControllerTest extends ControllerTestSupport {

	@WithMockUser()
	@DisplayName("보험 상품 추천 리포트를 생성함")
	@Test
	void createProductWithoutSellingStatus() throws Exception {
		// given
		InsuranceReportRequest request = InsuranceReportRequest.builder()
			.name("테스트")
			.birthDate(LocalDate.parse("2001-09-04"))
			.gender(Gender.MALE)
			.job(Job.STUDENT)
			.isMarried(false)
			.isDriver(false)
			.hasChild(false)
			.minPremium(10000)
			.maxPremium(50000)
			.diseaseHistory(List.of())
			.familyHistory(List.of(CANCER, RIVER))
			.coveragePreferences(Map.of(DEATH_BENEFIT, 1, ESSENTIAL_ONLY,2))
			.build();

		User mockUser = User.builder().id(1L).build();

		when(userService.userUpdate(anyLong(), any())).thenReturn(mockUser);
		when(insuranceReportService.recommend(notNull(),notNull()))
			.thenReturn(new IssueInsuranceReportResponse(UUID.randomUUID()));

		// when // then
		mockMvc.perform(
				post("/insurances/reports")
					.with(csrf())
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("201"))
			.andExpect(jsonPath("$.message").value("보험 추천 리포트 발급 성공"))
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@WithMockUser()
	@DisplayName("보장 순위 입력이 3 개를 초과하는 경우 예외가 발생함")
	@Test
	void createReportWithTooManyCoveragePreferences() throws Exception {
		// given
		InsuranceReportRequest request = InsuranceReportRequest.builder()
			.name("테스트")
			.birthDate(LocalDate.parse("2001-09-04"))
			.gender(Gender.MALE)
			.job(Job.STUDENT)
			.isMarried(false)
			.isDriver(false)
			.hasChild(false)
			.minPremium(10000)
			.maxPremium(50000)
			.diseaseHistory(List.of())
			.familyHistory(List.of(CANCER, RIVER))
			.coveragePreferences(Map.of(DEATH_BENEFIT, 1, ESSENTIAL_ONLY,2, SURGERY_COVERAGE, 3, RECOMMENDED_OPTION, 4))
			.build();

		User mockUser = User.builder().id(1L).build();

		when(userService.userUpdate(anyLong(), any())).thenReturn(mockUser);
		when(insuranceReportService.recommend(notNull(),notNull()))
			.thenReturn(new IssueInsuranceReportResponse(UUID.randomUUID()));

		// when // then
		mockMvc.perform(
				post("/insurances/reports")
					.with(csrf())
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("보장 순위 입력은 3 개를 초과할 수 없습니다."));
	}


	@WithMockUser()
	@DisplayName("최대 보험료가 최소 보험료 보다 10000 이상 크지 않다면 예외 발생")
	@Test
	void createReportOutOfPremiumRange() throws Exception {
		// given
		InsuranceReportRequest request = InsuranceReportRequest.builder()
			.name("테스트")
			.birthDate(LocalDate.parse("2001-09-04"))
			.gender(Gender.MALE)
			.job(Job.STUDENT)
			.isMarried(false)
			.isDriver(false)
			.hasChild(false)
			.minPremium(10000)
			.maxPremium(15000)
			.diseaseHistory(List.of())
			.familyHistory(List.of(CANCER, RIVER))
			.coveragePreferences(Map.of(DEATH_BENEFIT, 1, ESSENTIAL_ONLY, 2))
			.build();

		User mockUser = User.builder().id(1L).build();

		when(userService.userUpdate(anyLong(), any())).thenReturn(mockUser);
		when(insuranceReportService.recommend(notNull(),notNull()))
			.thenReturn(new IssueInsuranceReportResponse(UUID.randomUUID()));

		// when // then
		mockMvc.perform(
				post("/insurances/reports")
					.with(csrf())
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("최소 보험료는 최대 보험료보다 " + PREMIUM_RANGE + " 원 이상 적어야 합니다."));
	}

	@WithMockUser()
	@DisplayName("최소 보험료가 최대 보험료 보다 큰 경우 예외 발생")
	@Test
	void createReportWhenMinPremiumIsBiggerThanMaxPremium() throws Exception {
		// given
		InsuranceReportRequest request = InsuranceReportRequest.builder()
			.name("테스트")
			.birthDate(LocalDate.parse("2001-09-04"))
			.gender(Gender.MALE)
			.job(Job.STUDENT)
			.isMarried(false)
			.isDriver(false)
			.hasChild(false)
			.minPremium(20000)
			.maxPremium(10000)
			.diseaseHistory(List.of())
			.familyHistory(List.of(CANCER, RIVER))
			.coveragePreferences(Map.of(DEATH_BENEFIT, 1, ESSENTIAL_ONLY, 2))
			.build();

		User mockUser = User.builder().id(1L).build();

		when(userService.userUpdate(anyLong(), any())).thenReturn(mockUser);
		when(insuranceReportService.recommend(notNull(),notNull()))
			.thenReturn(new IssueInsuranceReportResponse(UUID.randomUUID()));

		// when // then
		mockMvc.perform(
				post("/insurances/reports")
					.with(csrf())
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("최소 보험료는 최대 보험료보다 " + PREMIUM_RANGE + " 원 이상 적어야 합니다."));
	}

	@WithMockUser()
	@DisplayName("이름에 단일 모음, 자음 등이 포함되는 경우 예외가 발생함")
	@Test
	void createReportWhenInvalidNameCharacters() throws Exception {
		// given
		InsuranceReportRequest request = InsuranceReportRequest.builder()
			.name("홍ㄱ동")
			.birthDate(LocalDate.parse("2001-09-04"))
			.gender(Gender.MALE)
			.job(Job.STUDENT)
			.isMarried(false)
			.isDriver(false)
			.hasChild(false)
			.minPremium(10000)
			.maxPremium(50000)
			.diseaseHistory(List.of())
			.familyHistory(List.of(CANCER, RIVER))
			.coveragePreferences(Map.of(
					DEATH_BENEFIT, 1,
					ESSENTIAL_ONLY,2,
					SURGERY_COVERAGE, 3
			))
			.build();

		User mockUser = User.builder().id(1L).build();

		when(userService.userUpdate(anyLong(), any())).thenReturn(mockUser);
		when(insuranceReportService.recommend(notNull(),notNull()))
			.thenReturn(new IssueInsuranceReportResponse(UUID.randomUUID()));

		// when // then
		mockMvc.perform(
				post("/insurances/reports")
					.with(csrf())
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").value("실명 입력 불가능한 값이 존재합니다."));
	}
}