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
public class DailyHospitalization {

	@Column(name = "daily_hospitalization_disease", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int disease;

	@Column(name = "daily_hospitalization_injury", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injury;
}
