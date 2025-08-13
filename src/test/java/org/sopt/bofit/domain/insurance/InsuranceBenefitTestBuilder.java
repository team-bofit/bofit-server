package org.sopt.bofit.domain.insurance;

import org.sopt.bofit.domain.insurance.entity.benefit.Cancer;
import org.sopt.bofit.domain.insurance.entity.benefit.Cerebrovascular;
import org.sopt.bofit.domain.insurance.entity.benefit.DailyHospitalization;
import org.sopt.bofit.domain.insurance.entity.benefit.Death;
import org.sopt.bofit.domain.insurance.entity.benefit.Disability;
import org.sopt.bofit.domain.insurance.entity.benefit.DiseaseSurgery;
import org.sopt.bofit.domain.insurance.entity.benefit.Heart;
import org.sopt.bofit.domain.insurance.entity.benefit.InjurySurgery;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.benefit.MajorDisease;
import org.sopt.bofit.domain.insurance.entity.benefit.Surgery;
import org.springframework.test.util.ReflectionTestUtils;

public abstract class InsuranceBenefitTestBuilder<
	SELF extends InsuranceBenefitTestBuilder<SELF, T>,
	T extends InsuranceBenefit> {

	private int generalCancerDiagnosis = 0;
	private int generalCancerSurgery = 0;
	private int atypicalCancerDiagnosis = 0;
	private int atypicalCancerSurgery = 0;

	private int hemorrhageDiagnosis = 0;
	private int hemorrhageSurgery = 0;
	private int infarctionDiagnosis = 0;
	private int infarctionSurgery = 0;
	private int otherDiagnosis = 0;
	private int otherSurgery = 0;

	private int acuteMyocardialInfarctionDiagnosis = 0;
	private int acuteMyocardialInfarctionSurgery = 0;
	private int arrhythmiaDiagnosis = 0;
	private int arrhythmiaSurgery = 0;
	private int extendedDiagnosis = 0;
	private int extendedSurgery = 0;
	private int ischemicDiagnosis = 0;
	private int ischemicSurgery = 0;

	private int disease = 0;
	private int diseaseType1 = 0;
	private int diseaseType2 = 0;
	private int diseaseType3 = 0;
	private int diseaseType4 = 0;
	private int diseaseType5 = 0;

	private int injury = 0;
	private int injuryType1 = 0;
	private int injuryType2 = 0;
	private int injuryType3 = 0;
	private int injuryType4 = 0;
	private int injuryType5 = 0;

	private int diseaseGE3PCT = 0;
	private int injuryGE3PCT = 0;

	private int deathDisease = 0;
	private int deathInjury = 0;

	private int dailyHospitalizationDisease = 0;
	private int dailyHospitalizationInjury = 0;

	public SELF withGeneralDiagnosis(int val) { this.generalCancerDiagnosis = val; return (SELF) this; }
	public SELF withGeneralSurgery(int val) { this.generalCancerSurgery = val; return (SELF) this; }
	public SELF withAtypicalDiagnosis(int val) { this.atypicalCancerDiagnosis = val; return (SELF) this; }
	public SELF withAtypicalSurgery(int val) { this.atypicalCancerSurgery = val; return (SELF) this; }

	public SELF withHemorrhageDiagnosis(int val) { this.hemorrhageDiagnosis = val; return (SELF) this; }
	public SELF withHemorrhageSurgery(int val) { this.hemorrhageSurgery = val; return (SELF) this; }
	public SELF withInfarctionDiagnosis(int val) { this.infarctionDiagnosis = val; return (SELF) this; }
	public SELF withInfarctionSurgery(int val) { this.infarctionSurgery = val; return (SELF) this; }
	public SELF withOtherDiagnosis(int val) { this.otherDiagnosis = val; return (SELF) this; }
	public SELF withOtherSurgery(int val) { this.otherSurgery = val; return (SELF) this; }

	public SELF withAcuteMyocardialInfarctionDiagnosis(int val) { this.acuteMyocardialInfarctionDiagnosis = val; return (SELF) this; }
	public SELF withAcuteMyocardialInfarctionSurgery(int val) { this.acuteMyocardialInfarctionSurgery = val; return (SELF) this; }
	public SELF withArrhythmiaDiagnosis(int val) { this.arrhythmiaDiagnosis = val; return (SELF) this; }
	public SELF withArrhythmiaSurgery(int val) { this.arrhythmiaSurgery = val; return (SELF) this; }
	public SELF withExtendedDiagnosis(int val) { this.extendedDiagnosis = val; return (SELF) this; }
	public SELF withExtendedSurgery(int val) { this.extendedSurgery = val; return (SELF) this; }
	public SELF withIschemicDiagnosis(int val) { this.ischemicDiagnosis = val; return (SELF) this; }
	public SELF withIschemicSurgery(int val) { this.ischemicSurgery = val; return (SELF) this; }

	public SELF withDisease(int val) { this.disease = val; return (SELF) this; }
	public SELF withDiseaseType1(int val) { this.diseaseType1 = val; return (SELF) this; }
	public SELF withDiseaseType2(int val) { this.diseaseType2 = val; return (SELF) this; }
	public SELF withDiseaseType3(int val) { this.diseaseType3 = val; return (SELF) this; }
	public SELF withDiseaseType4(int val) { this.diseaseType4 = val; return (SELF) this; }
	public SELF withDiseaseType5(int val) { this.diseaseType5 = val; return (SELF) this; }

	public SELF withInjury(int val) { this.injury = val; return (SELF) this; }
	public SELF withInjuryType1(int val) { this.injuryType1 = val; return (SELF) this; }
	public SELF withInjuryType2(int val) { this.injuryType2 = val; return (SELF) this; }
	public SELF withInjuryType3(int val) { this.injuryType3 = val; return (SELF) this; }
	public SELF withInjuryType4(int val) { this.injuryType4 = val; return (SELF) this; }
	public SELF withInjuryType5(int val) { this.injuryType5 = val; return (SELF) this; }

	public SELF withDiseaseGE3PCT(int val) { this.diseaseGE3PCT = val; return (SELF) this; }
	public SELF withInjuryGE3PCT(int val) { this.injuryGE3PCT = val; return (SELF) this; }

	public SELF withDeathDisease(int val) { this.deathDisease = val; return (SELF) this; }
	public SELF withDeathInjury(int val) { this.deathInjury = val; return (SELF) this; }

	public SELF withDailyHospitalizationDisease(int val) { this.dailyHospitalizationDisease = val; return (SELF) this; }
	public SELF withDailyHospitalizationInjury(int val) { this.dailyHospitalizationInjury = val; return (SELF) this; }


	protected void applyCommonFields(T target) {

		Cancer cancer = Cancer.builder()
			.generalDiagnosis(generalCancerDiagnosis)
			.generalSurgery(generalCancerSurgery)
			.atypicalDiagnosis(atypicalCancerDiagnosis)
			.atypicalSurgery(atypicalCancerSurgery)
			.build();

		Cerebrovascular cerebrovascular = Cerebrovascular.builder()
			.hemorrhageDiagnosis(hemorrhageDiagnosis)
			.hemorrhageSurgery(hemorrhageSurgery)
			.infarctionDiagnosis(infarctionDiagnosis)
			.infarctionSurgery(infarctionSurgery)
			.otherDiagnosis(otherDiagnosis)
			.otherSurgery(otherSurgery)
			.build();

		Heart heart = Heart.builder()
			.acuteMyocardialInfarctionDiagnosis(acuteMyocardialInfarctionDiagnosis)
			.acuteMyocardialInfarctionSurgery(acuteMyocardialInfarctionSurgery)
			.arrhythmiaDiagnosis(arrhythmiaDiagnosis)
			.arrhythmiaSurgery(arrhythmiaSurgery)
			.extendedDiagnosis(extendedDiagnosis)
			.extendedSurgery(extendedSurgery)
			.ischemicDiagnosis(ischemicDiagnosis)
			.ischemicSurgery(ischemicSurgery)
			.build();

		MajorDisease majorDisease = MajorDisease.builder()
			.cancer(cancer)
			.cerebrovascular(cerebrovascular)
			.heart(heart)
			.build();

		DiseaseSurgery diseaseSurgery = DiseaseSurgery.builder()
			.disease(disease)
			.diseaseType1(diseaseType1)
			.diseaseType2(diseaseType2)
			.diseaseType3(diseaseType3)
			.diseaseType4(diseaseType4)
			.diseaseType5(diseaseType5)
			.build();

		InjurySurgery injurySurgery = InjurySurgery.builder()
			.injury(injury)
			.injuryType1(injuryType1)
			.injuryType2(injuryType2)
			.injuryType3(injuryType3)
			.injuryType4(injuryType4)
			.injuryType5(injuryType5)
			.build();

		Surgery surgery = Surgery.builder()
			.diseaseSurgery(diseaseSurgery)
			.injurySurgery(injurySurgery)
			.build();

		Disability disability = Disability.builder()
			.diseaseGE3PCT(diseaseGE3PCT)
			.injuryGE3PCT(injuryGE3PCT)
			.build();

		DailyHospitalization dailyHospitalization = DailyHospitalization.builder()
			.disease(dailyHospitalizationDisease)
			.injury(dailyHospitalizationInjury)
			.build();

		Death death = Death.builder()
			.disease(deathDisease)
			.injury(deathInjury)
			.build();

		ReflectionTestUtils.setField(target, "majorDisease", majorDisease);
		ReflectionTestUtils.setField(target, "surgery", surgery);
		ReflectionTestUtils.setField(target, "disability", disability);
		ReflectionTestUtils.setField(target, "dailyHospitalization", dailyHospitalization);
		ReflectionTestUtils.setField(target, "death", death);
	}

	public abstract T build();

}
