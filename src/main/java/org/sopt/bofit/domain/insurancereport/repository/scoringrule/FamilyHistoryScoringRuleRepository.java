package org.sopt.bofit.domain.insurancereport.repository.scoringrule;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.familyhistory.FamilyHistoryScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyHistoryScoringRuleRepository extends JpaRepository<FamilyHistoryScoringRule, Long> {
}
