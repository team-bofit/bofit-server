package org.sopt.bofit.domain.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class DeathBenefit {

	@Column(name = "disease_death_benefit", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer disease;

	@Column(name = "injury_death_benefit", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer injury;
}
