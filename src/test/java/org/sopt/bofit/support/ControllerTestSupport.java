package org.sopt.bofit.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sopt.bofit.config.TestConfig;
import org.sopt.bofit.domain.insurance.controller.InsuranceController;
import org.sopt.bofit.domain.insurance.service.InsuranceService;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.domain.user.service.UserService;
import org.sopt.bofit.global.config.WebConfig;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {InsuranceController.class},
	excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = {WebConfig.class})
	})
@ActiveProfiles("test")
@Import(TestConfig.class)
public abstract class ControllerTestSupport {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected JwtUtil jwtUtil;

	@MockBean
	protected UserService userService;

	@MockBean
	protected InsuranceReportService insuranceReportService;

    @MockBean
    protected InsuranceService insuranceService;
}
