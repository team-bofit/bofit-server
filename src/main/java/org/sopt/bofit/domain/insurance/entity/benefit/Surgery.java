package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Surgery {

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
}
