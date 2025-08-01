package org.sopt.bofit.domain.insurancereport.service.scoringrule;

import static org.sopt.bofit.global.constant.CacheConstant.*;

import java.util.List;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.diseasehistory.DiseaseHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory.FamilyHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.select.SelectedScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoScoringRule;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.DiseaseHistoryScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.FamilyHistoryScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.SelectedScoringRuleRepository;
import org.sopt.bofit.domain.insurancereport.repository.scoringrule.UserInfoScoringRuleRepository;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoringRuleProvider {
	private final UserInfoScoringRuleRepository userInfoScoringRuleRepository;
	private final DiseaseHistoryScoringRuleRepository diseaseHistoryScoringRuleRepository;
	private final FamilyHistoryScoringRuleRepository familyHistoryScoringRuleRepository;
	private final SelectedScoringRuleRepository selectedScoringRuleRepository;

	@Cacheable(cacheNames = USER_INFO_SCORING_RULE_CACHE_NAME, key = "#userInfoRuleType")
	public List<UserInfoScoringRule> findAllUserInfoRuleType(UserInfoRuleType userInfoRuleType){
		return userInfoScoringRuleRepository.findAllByUserInfoRuleType(userInfoRuleType);
	}

	@Cacheable(cacheNames = DISEASE_HISTORY_SCORING_RULE_CACHE_NAME, key = "#diagnosedDisease")
	public List<DiseaseHistoryScoringRule> findAllDiseaseHistory(DiagnosedDisease diagnosedDisease){
		return diseaseHistoryScoringRuleRepository.findAllByDiagnosedDisease(diagnosedDisease);
	}

	@Cacheable(cacheNames = FAMILY_HISTORY_SCORING_RULE_CACHE_NAME, key = "#diagnosedDisease")
	public List<FamilyHistoryScoringRule> findAllFamilyHistory(DiagnosedDisease diagnosedDisease){
		return familyHistoryScoringRuleRepository.findAllByDiagnosedDisease(diagnosedDisease);
	}

	@Cacheable(cacheNames = SELECTED_SCORING_RULE_CACHE_NAME, key = "#coveragePreference")
	public List<SelectedScoringRule> findAllCoveragePreference(CoveragePreference coveragePreference){
		return selectedScoringRuleRepository.findAllByCoveragePreference(coveragePreference);
	}

}
