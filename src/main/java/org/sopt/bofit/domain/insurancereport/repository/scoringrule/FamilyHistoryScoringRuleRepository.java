package org.sopt.bofit.domain.insurancereport.repository.scoringrule;

import java.util.List;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.diseasehistory.DiseaseHistoryScoringRule;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory.FamilyHistoryScoringRule;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyHistoryScoringRuleRepository extends JpaRepository<FamilyHistoryScoringRule, Long> {
	List<FamilyHistoryScoringRule> findAllByDiagnosedDisease(DiagnosedDisease diagnosedDisease);
}
