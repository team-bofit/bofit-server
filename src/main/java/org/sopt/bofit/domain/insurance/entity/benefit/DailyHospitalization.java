package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class DailyHospitalization {

	@Column(name = "daily_hospitalization_disease", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int disease;

	@Column(name = "daily_hospitalization_injury", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injury;
}
