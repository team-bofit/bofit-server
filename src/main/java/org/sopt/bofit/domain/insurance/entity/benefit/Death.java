package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Death {

	@Column(name = "disease_death_benefit", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int disease;

	@Column(name = "injury_death_benefit", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injury;
}
