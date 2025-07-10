package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DiseaseSurgery implements SurgeryType {
	@Column(name = "disease_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int disease;

	@Column(name = "disease_surgery_type1", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseType1;

	@Column(name = "disease_surgery_type2", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseType2;

	@Column(name = "disease_surgery_type3", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseType3;

	@Column(name = "disease_surgery_type4", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseType4;

	@Column(name = "disease_surgery_type5", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int diseaseType5;

	@Override
	public int getGeneral() {
		return disease;
	}

	@Override
	public int getType1() {
		return diseaseType1;
	}

	@Override
	public int getType2() {
		return diseaseType2;
	}

	@Override
	public int getType3() {
		return diseaseType3;
	}

	@Override
	public int getType4() {
		return diseaseType4;
	}

	@Override
	public int getType5() {
		return diseaseType5;
	}
}
