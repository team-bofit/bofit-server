package org.sopt.bofit.domain.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class DiseaseSurgeryBenefit {

	@Column(name = "disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer general;

	@Column(name = "disease_surgery_type1", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer type1;

	@Column(name = "disease_surgery_type2", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer type2;

	@Column(name = "disease_surgery_type3", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer type3;

	@Column(name = "disease_surgery_type4", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer type4;

	@Column(name = "disease_surgery_type5", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private Integer type5;

}
