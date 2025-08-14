package org.sopt.bofit.domain.insurance.controller;


import static org.mockito.Mockito.*;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.*;
import static org.sopt.bofit.domain.user.entity.constant.CoveragePreference.*;
import static org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.config.TestConfig;
import org.sopt.bofit.domain.insurancereport.annotation.PremiumRange;
import org.sopt.bofit.domain.insurancereport.dto.request.InsuranceReportRequest;
import org.sopt.bofit.domain.insurancereport.dto.response.IssueInsuranceReportResponse;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.annotation.LoginUserId;
import org.sopt.bofit.global.config.SecurityConfig;
import org.sopt.bofit.global.config.WebConfig;
import org.sopt.bofit.global.oauth.jwt.JwtAuthenticationFilter;
import org.sopt.bofit.global.oauth.jwt.JwtTokenAuthentication;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.sopt.bofit.global.oauth.jwt.LoginArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = InsuranceController.class)


@ActiveProfiles("test")
class InsuranceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private InsuranceReportService insuranceReportService;

	@MockBean
	private JwtUtil jwtUtil;

	@BeforeEach
	public void setUp(){
		Authentication authentication = new JwtTokenAuthentication(10L);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

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

}