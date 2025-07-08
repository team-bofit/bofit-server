package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Heart {

	@Column(name = "acute_myocardial_infarction_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int acuteMyocardialInfarctionDiagnosis;

	@Column(name = "ischemic_heart_disease_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int ischemicDiagnosis;

	@Column(name = "extended_heart_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int extendedDiagnosis;

	@Column(name = "arrhythmia_heart_failure_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int arrhythmiaDiagnosis;

	@Column(name = "acute_myocardial_infarction_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int acuteMyocardialInfarctionSurgery;

	@Column(name = "ischemic_heart_disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int ischemicSurgery;

	@Column(name = "extended_heart_disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int extendedSurgery;

	@Column(name = "arrhythmia_heart_failure_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int arrhythmiaSurgery;
}
