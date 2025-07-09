package org.sopt.bofit.domain.insurancereport.entity.scoringrule.select;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SelectedScoringRule extends InsuranceScoringRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "selected_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CoveragePreference coveragePreference;

}
