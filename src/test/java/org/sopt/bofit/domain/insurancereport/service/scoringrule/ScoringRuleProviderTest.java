package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionCoverage.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator.*;
import static org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.ConditionOperator;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.diseasehistory.DiseaseHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory.FamilyHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.select.SelectedScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoScoringRule;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.DiseaseHistoryScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.FamilyHistoryScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.SelectedScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.UserInfoScoringRuleRepository;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ScoringRuleProviderTest {

	@Autowired
	private ScoringRuleProvider scoringRuleProvider;
	
	@Autowired
	private UserInfoScoringRuleRepository userInfoScoringRuleRepository;
	
	@Autowired
	private DiseaseHistoryScoringRuleRepository diseaseHistoryScoringRuleRepository;

	@Autowired
	private FamilyHistoryScoringRuleRepository familyHistoryScoringRuleRepository;

	@Autowired
	private SelectedScoringRuleRepository selectedScoringRuleRepository;

	@AfterEach
	void clearInAfterTest(){
		userInfoScoringRuleRepository.deleteAllInBatch();
		diseaseHistoryScoringRuleRepository.deleteAllInBatch();
		familyHistoryScoringRuleRepository.deleteAllInBatch();
		selectedScoringRuleRepository.deleteAllInBatch();
	}

	@DisplayName("파라미터로 전달하는 UserInfoRuleType 에 해당하는 UserInfoRule 를 전부 정상적으로 반환함.")
	@Test
	void getUserInfoRuleTest(){
	    // given
		UserInfoScoringRule hasChildScoringRule1 = UserInfoScoringRule.create(
			HAS_CHILD,
			PREMIUM,
			EXIST,
			1000,
			5d
		);

		UserInfoScoringRule hasChildScoringRule2 = UserInfoScoringRule.create(
			HAS_CHILD,
			GENERAL_CANCER_DIAGNOSIS,
			EXIST,
			2000,
			5d
		);

		UserInfoScoringRule hasChildScoringRule3 = UserInfoScoringRule.create(
			HAS_CHILD,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		UserInfoScoringRule driverScoringRule2 = UserInfoScoringRule.create(
			DRIVER,
			PREMIUM,
			EXIST,
			2000,
			5d
		);

		userInfoScoringRuleRepository.saveAll(List.of(
			hasChildScoringRule1,
			hasChildScoringRule2,
			hasChildScoringRule3,
			driverScoringRule2
		));
		
	    // when
		List<UserInfoScoringRule> results = scoringRuleProvider.findAllUserInfoRuleType(HAS_CHILD);

		// then
		assertThat(results).hasSize(3)
			.extracting("userInfoRuleType", "conditionCoverage")
			.containsExactlyInAnyOrder(
				Tuple.tuple(HAS_CHILD, PREMIUM),
				Tuple.tuple(HAS_CHILD, GENERAL_CANCER_DIAGNOSIS),
				Tuple.tuple(HAS_CHILD, ATYPICAL_CANCER_SURGERY)
			);
	}

	@DisplayName("파라미터로 전달하는 DiagnosedDisease 에 해당하는 DiseaseHistoryScoringRule 를 전부 정상적으로 반환함.")
	@Test
	void getDiseaseHistoryRuleTest(){
		// given
		DiseaseHistoryScoringRule cancerScoringRule1 = DiseaseHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			PREMIUM,
			EXIST,
			1000,
			5d
		);

		DiseaseHistoryScoringRule cancerScoringRule2 = DiseaseHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			GENERAL_CANCER_DIAGNOSIS,
			EXIST,
			2000,
			5d
		);

		DiseaseHistoryScoringRule cancerScoringRule3 = DiseaseHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		DiseaseHistoryScoringRule chronicScoringRule1 = DiseaseHistoryScoringRule.create(
			DiagnosedDisease.CHRONIC,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		diseaseHistoryScoringRuleRepository.saveAll(List.of(
			cancerScoringRule1,
			cancerScoringRule2,
			cancerScoringRule3,
			chronicScoringRule1
		));

		// when
		List<DiseaseHistoryScoringRule> results = scoringRuleProvider.findAllDiseaseHistory(DiagnosedDisease.CANCER);

		// then
		assertThat(results).hasSize(3)
			.extracting("diagnosedDisease", "conditionCoverage")
			.containsExactlyInAnyOrder(
				Tuple.tuple(DiagnosedDisease.CANCER, PREMIUM),
				Tuple.tuple(DiagnosedDisease.CANCER, GENERAL_CANCER_DIAGNOSIS),
				Tuple.tuple(DiagnosedDisease.CANCER, ATYPICAL_CANCER_SURGERY)
			);
	}

	@DisplayName("파라미터로 전달하는 DiagnosedDisease 에 해당하는 FamilyHistoryScoringRule 를 전부 정상적으로 반환함.")
	@Test
	void getFamilyHistoryRuleTest(){
		// given
		FamilyHistoryScoringRule cancerScoringRule1 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			PREMIUM,
			EXIST,
			1000,
			5d
		);

		FamilyHistoryScoringRule cancerScoringRule2 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			GENERAL_CANCER_DIAGNOSIS,
			EXIST,
			2000,
			5d
		);

		FamilyHistoryScoringRule cancerScoringRule3 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CANCER,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		FamilyHistoryScoringRule chronicScoringRule1 = FamilyHistoryScoringRule.create(
			DiagnosedDisease.CHRONIC,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		familyHistoryScoringRuleRepository.saveAll(List.of(
			cancerScoringRule1,
			cancerScoringRule2,
			cancerScoringRule3,
			chronicScoringRule1
		));

		// when
		List<FamilyHistoryScoringRule> results = scoringRuleProvider.findAllFamilyHistory(DiagnosedDisease.CANCER);

		// then
		assertThat(results).hasSize(3)
			.extracting("diagnosedDisease", "conditionCoverage")
			.containsExactlyInAnyOrder(
				Tuple.tuple(DiagnosedDisease.CANCER, PREMIUM),
				Tuple.tuple(DiagnosedDisease.CANCER, GENERAL_CANCER_DIAGNOSIS),
				Tuple.tuple(DiagnosedDisease.CANCER, ATYPICAL_CANCER_SURGERY)
			);
	}

	@DisplayName("파라미터로 전달하는 CoveragePreference 에 해당하는 SelectedScoringRule 를 전부 정상적으로 반환함.")
	@Test
	void getSelectedScoringRuleTest(){
		// given
		SelectedScoringRule maximumCoverageScoringRule1 = SelectedScoringRule.create(
			CoveragePreference.MAXIMUM_COVERAGE,
			PREMIUM,
			EXIST,
			1000,
			5d
		);

		SelectedScoringRule maximumCoverageScoringRule2 = SelectedScoringRule.create(
			CoveragePreference.MAXIMUM_COVERAGE,
			GENERAL_CANCER_DIAGNOSIS,
			EXIST,
			2000,
			5d
		);

		SelectedScoringRule maximumCoverageScoringRule3 = SelectedScoringRule.create(
			CoveragePreference.MAXIMUM_COVERAGE,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		SelectedScoringRule majorDiseaseScoringRule1 = SelectedScoringRule.create(
			CoveragePreference.MAJOR_DISEASE,
			ATYPICAL_CANCER_SURGERY,
			ConditionOperator.GE,
			3000,
			10d
		);

		selectedScoringRuleRepository.saveAll(List.of(
			maximumCoverageScoringRule1,
			maximumCoverageScoringRule2,
			maximumCoverageScoringRule3,
			majorDiseaseScoringRule1
		));

		// when
		List<SelectedScoringRule> results = scoringRuleProvider.findAllCoveragePreference(CoveragePreference.MAXIMUM_COVERAGE);

		// then
		assertThat(results).hasSize(3)
			.extracting("coveragePreference", "conditionCoverage")
			.containsExactlyInAnyOrder(
				Tuple.tuple(CoveragePreference.MAXIMUM_COVERAGE, PREMIUM),
				Tuple.tuple(CoveragePreference.MAXIMUM_COVERAGE, GENERAL_CANCER_DIAGNOSIS),
				Tuple.tuple(CoveragePreference.MAXIMUM_COVERAGE, ATYPICAL_CANCER_SURGERY)
			);
	}

}