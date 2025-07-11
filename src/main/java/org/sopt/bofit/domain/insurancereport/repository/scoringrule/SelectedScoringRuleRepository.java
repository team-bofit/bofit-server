package org.sopt.bofit.domain.insurancereport.repository.scoringrule;

import java.util.List;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.select.SelectedScoringRule;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedScoringRuleRepository extends JpaRepository<SelectedScoringRule, Long> {
	List<SelectedScoringRule> findAllByCoveragePreference(CoveragePreference coveragePreference);
}
