package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Cancer {

	@Column(name = "general_cancer_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int generalDiagnosis;

	@Column(name = "atypical_cancer_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int atypicalDiagnosis;

	@Column(name = "general_cancer_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int generalSurgery;

	@Column(name = "atypical_cancer_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int atypicalSurgery;
}
