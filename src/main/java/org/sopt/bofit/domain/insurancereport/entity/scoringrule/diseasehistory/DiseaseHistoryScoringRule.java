package org.sopt.bofit.domain.insurancereport.entity.scoringrule.diseasehistory;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.InsuranceScoringRule;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DiseaseHistoryScoringRule extends InsuranceScoringRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "disease_history_scoring_rule_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private DiagnosedDisease diagnosedDisease;
}
