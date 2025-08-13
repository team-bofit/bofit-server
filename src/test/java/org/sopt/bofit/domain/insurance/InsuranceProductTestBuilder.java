package org.sopt.bofit.domain.insurance;

import org.sopt.bofit.domain.insurance.entity.benefit.Cancer;
import org.sopt.bofit.domain.insurance.entity.benefit.Cerebrovascular;
import org.sopt.bofit.domain.insurance.entity.benefit.DailyHospitalization;
import org.sopt.bofit.domain.insurance.entity.benefit.Death;
import org.sopt.bofit.domain.insurance.entity.benefit.Disability;
import org.sopt.bofit.domain.insurance.entity.benefit.DiseaseSurgery;
import org.sopt.bofit.domain.insurance.entity.benefit.Heart;
import org.sopt.bofit.domain.insurance.entity.benefit.InjurySurgery;
import org.sopt.bofit.domain.insurance.entity.benefit.MajorDisease;
import org.sopt.bofit.domain.insurance.entity.benefit.Surgery;
import org.sopt.bofit.domain.insurance.entity.product.BasicInformation;
import org.sopt.bofit.domain.insurance.entity.product.ExtraInformation;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;

public class InsuranceProductTestBuilder {

	private String name = "테스트용 상품명";
	private String company = "테스트용 회사명";
	private int premium = 10000;
	private int maturityAge = 100;
	private int minEnrollmentAge = 0;
	private int maxEnrollmentAge = 100;
	private int paymentPeriodYears = 20;

	private String remarks = "테스트용 비고";
	private String externalUri = "테스용 uri";
	private String source = "테스용 source";

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

	private int generalDiagnosis = 0;
	private int generalSurgery = 0;
	private int atypicalDiagnosis = 0;
	private int atypicalSurgery = 0;

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

	private InsuranceStatus status = InsuranceStatus.SELLING;

	public InsuranceProductTestBuilder withName(String name) { this.name = name; return this; }
	public InsuranceProductTestBuilder withCompany(String company) { this.company = company; return this; }
	public InsuranceProductTestBuilder withPremium(int premium) { this.premium = premium; return this; }
	public InsuranceProductTestBuilder withMaturityAge(int age) { this.maturityAge = age; return this; }
	public InsuranceProductTestBuilder withMinEnrollmentAge(int age) { this.minEnrollmentAge = age; return this; }
	public InsuranceProductTestBuilder withMaxEnrollmentAge(int age) { this.maxEnrollmentAge = age; return this; }
	public InsuranceProductTestBuilder withPaymentPeriodYears(int years) { this.paymentPeriodYears = years; return this; }
	public InsuranceProductTestBuilder withRemarks(String remarks) { this.remarks = remarks; return this; }

	public InsuranceProductTestBuilder withHemorrhageDiagnosis(int val) { this.hemorrhageDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withHemorrhageSurgery(int val) { this.hemorrhageSurgery = val; return this; }
	public InsuranceProductTestBuilder withInfarctionDiagnosis(int val) { this.infarctionDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withInfarctionSurgery(int val) { this.infarctionSurgery = val; return this; }
	public InsuranceProductTestBuilder withOtherDiagnosis(int val) { this.otherDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withOtherSurgery(int val) { this.otherSurgery = val; return this; }

	public InsuranceProductTestBuilder withAcuteMyocardialInfarctionDiagnosis(int val) { this.acuteMyocardialInfarctionDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withAcuteMyocardialInfarctionSurgery(int val) { this.acuteMyocardialInfarctionSurgery = val; return this; }
	public InsuranceProductTestBuilder withArrhythmiaDiagnosis(int val) { this.arrhythmiaDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withArrhythmiaSurgery(int val) { this.arrhythmiaSurgery = val; return this; }
	public InsuranceProductTestBuilder withExtendedDiagnosis(int val) { this.extendedDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withExtendedSurgery(int val) { this.extendedSurgery = val; return this; }
	public InsuranceProductTestBuilder withIschemicDiagnosis(int val) { this.ischemicDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withIschemicSurgery(int val) { this.ischemicSurgery = val; return this; }

	public InsuranceProductTestBuilder withGeneralDiagnosis(int val) { this.generalDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withGeneralSurgery(int val) { this.generalSurgery = val; return this; }
	public InsuranceProductTestBuilder withAtypicalDiagnosis(int val) { this.atypicalDiagnosis = val; return this; }
	public InsuranceProductTestBuilder withAtypicalSurgery(int val) { this.atypicalSurgery = val; return this; }

	public InsuranceProductTestBuilder withDisease(int val) { this.disease = val; return this; }
	public InsuranceProductTestBuilder withDiseaseType1(int val) { this.diseaseType1 = val; return this; }
	public InsuranceProductTestBuilder withDiseaseType2(int val) { this.diseaseType2 = val; return this; }
	public InsuranceProductTestBuilder withDiseaseType3(int val) { this.diseaseType3 = val; return this; }
	public InsuranceProductTestBuilder withDiseaseType4(int val) { this.diseaseType4 = val; return this; }
	public InsuranceProductTestBuilder withDiseaseType5(int val) { this.diseaseType5 = val; return this; }

	public InsuranceProductTestBuilder withInjury(int val) { this.injury = val; return this; }
	public InsuranceProductTestBuilder withInjuryType1(int val) { this.injuryType1 = val; return this; }
	public InsuranceProductTestBuilder withInjuryType2(int val) { this.injuryType2 = val; return this; }
	public InsuranceProductTestBuilder withInjuryType3(int val) { this.injuryType3 = val; return this; }
	public InsuranceProductTestBuilder withInjuryType4(int val) { this.injuryType4 = val; return this; }
	public InsuranceProductTestBuilder withInjuryType5(int val) { this.injuryType5 = val; return this; }

	public InsuranceProductTestBuilder withDiseaseGE3PCT(int val) { this.diseaseGE3PCT = val; return this; }
	public InsuranceProductTestBuilder withInjuryGE3PCT(int val) { this.injuryGE3PCT = val; return this; }

	public InsuranceProductTestBuilder withDeathDisease(int val) { this.deathDisease = val; return this; }
	public InsuranceProductTestBuilder withDeathInjury(int val) { this.deathInjury = val; return this; }

	public InsuranceProductTestBuilder withDailyHospitalizationDisease(int val) { this.dailyHospitalizationDisease = val; return this; }
	public InsuranceProductTestBuilder withDailyHospitalizationInjury(int val) { this.dailyHospitalizationInjury = val; return this; }

	public InsuranceProductTestBuilder withStatus(InsuranceStatus status) { this.status = status; return this; }

	public InsuranceProduct build() {
		BasicInformation basicInformation = BasicInformation.builder()
			.name(name)
			.company(company)
			.premium(premium)
			.maturityAge(maturityAge)
			.minEnrollmentAge(minEnrollmentAge)
			.maxEnrollmentAge(maxEnrollmentAge)
			.paymentPeriodYears(paymentPeriodYears)
			.build();

		ExtraInformation extraInformation = ExtraInformation.builder()
			.remarks(remarks)
			.externalUri(externalUri)
			.source(source)
			.build();

		Cancer cancer = Cancer.builder()
			.generalDiagnosis(generalDiagnosis)
			.generalSurgery(generalSurgery)
			.atypicalDiagnosis(atypicalDiagnosis)
			.atypicalSurgery(atypicalSurgery)
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

		Death death = Death.builder()
			.disease(deathDisease)
			.injury(deathInjury)
			.build();

		DailyHospitalization dailyHospitalization = DailyHospitalization.builder()
			.disease(dailyHospitalizationDisease)
			.injury(dailyHospitalizationInjury)
			.build();

		return InsuranceProduct.builder()
			.status(status)
			.basicInformation(basicInformation)
			.extraInformation(extraInformation)
			.majorDisease(majorDisease)
			.surgery(surgery)
			.dailyHospitalization(dailyHospitalization)
			.disability(disability)
			.death(death)
			.build();
	}
}
