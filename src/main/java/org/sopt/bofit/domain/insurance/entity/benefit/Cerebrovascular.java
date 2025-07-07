package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Cerebrovascular {

	@Column(name = "cerebral_hemorrhage_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int hemorrhageDiagnosis;

	@Column(name = "cerebral_infarction_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int infarctionDiagnosis;

	@Column(name = "other_cerebrovascular_diagnosis", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int otherDiagnosis;

	@Column(name = "cerebral_hemorrhage_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int hemorrhageSurgery;

	@Column(name = "cerebral_infarction_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int infarctionSurgery;

	@Column(name = "other_cerebrovascular_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int otherSurgery;
}
