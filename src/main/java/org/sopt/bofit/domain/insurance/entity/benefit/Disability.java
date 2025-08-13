package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disability {

	@Column(name = "disease_disability_ge_3_pct", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseGE3PCT;

	@Column(name = "injury_disability_ge_3_pct", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryGE3PCT;
}
