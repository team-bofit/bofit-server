package org.sopt.bofit.domain.insurancereport.service.scoringrule;

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
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoringRuleProvider {
	private final UserInfoScoringRuleRepository userInfoScoringRuleRepository;
	private final DiseaseHistoryScoringRuleRepository diseaseHistoryScoringRuleRepository;
	private final FamilyHistoryScoringRuleRepository familyHistoryScoringRuleRepository;
	private final SelectedScoringRuleRepository selectedScoringRuleRepository;

	public List<UserInfoScoringRule> findAllUserInfoRuleType(UserInfoRuleType userInfoRuleType){
		return userInfoScoringRuleRepository.findAllByUserInfoRuleType(userInfoRuleType);
	}

	public List<DiseaseHistoryScoringRule> findAllDiseaseHistory(DiagnosedDisease diagnosedDisease){
		return diseaseHistoryScoringRuleRepository.findAllByDiagnosedDisease(diagnosedDisease);
	}

	public List<FamilyHistoryScoringRule> findAllFamilyHistory(DiagnosedDisease diagnosedDisease){
		return familyHistoryScoringRuleRepository.findAllByDiagnosedDisease(diagnosedDisease);
	}

	public List<SelectedScoringRule> findAllCoveragePreference(CoveragePreference coveragePreference){
		return selectedScoringRuleRepository.findAllByCoveragePreference(coveragePreference);
	}

}
