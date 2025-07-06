package org.sopt.bofit.domain.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class MajorSurgeryBenefit {

	@Column(name = "general_cancer_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int generalCancer;

	@Column(name = "atypical_cancer_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int atypicalCancer;

	@Column(name = "cerebral_hemorrhage_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int cerebralHemorrhage;

	@Column(name = "cerebral_infarction_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int cerebralInfarction;

	@Column(name = "other_cerebrovascular_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int otherCerebrovascular;

	@Column(name = "acute_myocardial_infarction_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int acuteMyocardialInfarction;

	@Column(name = "ischemic_heart_disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int ischemicHeartDisease;

	@Column(name = "extended_heart_disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int extendedHeartDisease;

	@Column(name = "arrhythmia_heart_failure_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int arrhythmiaHeartFailure;
}
