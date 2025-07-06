package org.sopt.bofit.domain.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class MajorSurgeryBenefit {

	@Column(name = "general_cancer_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer generalCancer;

	@Column(name = "atypical_cancer_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer atypicalCancer;

	@Column(name = "cerebral_hemorrhage_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer cerebralHemorrhage;

	@Column(name = "cerebral_infarction_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer cerebralInfarction;

	@Column(name = "other_cerebrovascular_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer otherCerebrovascular;

	@Column(name = "acute_myocardial_infarction_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer acuteMyocardialInfarction;

	@Column(name = "ischemic_heart_disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer ischemicHeartDisease;

	@Column(name = "extended_heart_disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer extendedHeartDisease;

	@Column(name = "arrhythmia_heart_failure_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer arrhythmiaHeartFailure;
}
