package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Disability {

	@Column(name = "disease_disability_ge_3_pct", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseGE3PCT;

	@Column(name = "injury_disability_ge_3_pct", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryGE3PCT;
}
