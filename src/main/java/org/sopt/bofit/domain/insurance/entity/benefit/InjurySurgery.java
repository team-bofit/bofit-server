package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InjurySurgery implements SurgeryType {
	@Column(name = "injury_surgery", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injury;

	@Column(name = "injury_surgery_type1", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryType1;

	@Column(name = "injury_surgery_type2", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryType2;

	@Column(name = "injury_surgery_type3", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryType3;

	@Column(name = "injury_surgery_type4", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryType4;

	@Column(name = "injury_surgery_type5", nullable = false,
		columnDefinition = "SMALLINT UNSIGNED DEFAULT 0")
	private int injuryType5;

	@Override
	public int getGeneral() {
		return injury;
	}

	@Override
	public int getType1() {
		return injuryType1;
	}

	@Override
	public int getType2() {
		return injuryType2;
	}

	@Override
	public int getType3() {
		return injuryType3;
	}

	@Override
	public int getType4() {
		return injuryType4;
	}

	@Override
	public int getType5() {
		return injuryType5;
	}
}
