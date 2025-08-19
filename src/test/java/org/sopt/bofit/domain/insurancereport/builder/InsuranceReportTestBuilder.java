package org.sopt.bofit.domain.insurancereport.builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.domain.user.entity.User;

public class InsuranceReportTestBuilder{

	private UUID id;
	private User user = User.builder()
		.id(1L)
		.build();
	private InsuranceProduct product;
	private InsuranceStatistic statistic;
	private ReportRationale reportRationale = new ReportRationale(
		List.of("테스트용 이유1", "테스트용 이유2"),
		List.of("테스트용 키워드 칩", "테스트용 키워드 칩2")
	);

	private CoverageStatus majorDisease = CoverageStatus.ENOUGH;
	private CoverageStatus cancer = CoverageStatus.ENOUGH;
	private CoverageStatus cerebrovascular = CoverageStatus.ENOUGH;
	private CoverageStatus heartDisease = CoverageStatus.ENOUGH;

	private CoverageStatus surgery = CoverageStatus.ENOUGH;
	private CoverageStatus diseaseSurgery = CoverageStatus.ENOUGH;
	private CoverageStatus diseaseTypeSurgery = CoverageStatus.ENOUGH;
	private CoverageStatus injurySurgery = CoverageStatus.ENOUGH;
	private CoverageStatus injuryTypeSurgery = CoverageStatus.ENOUGH;

	private CoverageStatus dailyHospitalization = CoverageStatus.ENOUGH;
	private CoverageStatus diseaseDailyHospitalization = CoverageStatus.ENOUGH;
	private CoverageStatus injuryDailyHospitalization = CoverageStatus.ENOUGH;

	private CoverageStatus disability = CoverageStatus.ENOUGH;
	private CoverageStatus diseaseDisability = CoverageStatus.ENOUGH;
	private CoverageStatus injuryDisability = CoverageStatus.ENOUGH;

	private CoverageStatus death = CoverageStatus.ENOUGH;
	private CoverageStatus diseaseDeath = CoverageStatus.ENOUGH;
	private CoverageStatus injuryDeath = CoverageStatus.ENOUGH;

	private LocalDateTime createdAt = LocalDateTime.now();

	public InsuranceReportTestBuilder withUser(User user) {
		this.user = user;
		return this;
	}

	public InsuranceReportTestBuilder withProduct(InsuranceProduct product) {
		this.product = product;
		return this;
	}

	public InsuranceReportTestBuilder withStatistic(InsuranceStatistic statistic) {
		this.statistic = statistic;
		return this;
	}

	public InsuranceReportTestBuilder withReportRationale(ReportRationale reportRationale) {
		this.reportRationale = reportRationale;
		return this;
	}

	public InsuranceReportTestBuilder withMajorDisease(CoverageStatus majorDisease) {
		this.majorDisease = majorDisease;
		return this;
	}

	public InsuranceReportTestBuilder withCancer(CoverageStatus cancer) {
		this.cancer = cancer;
		return this;
	}

	public InsuranceReportTestBuilder withCerebrovascular(CoverageStatus cerebrovascular) {
		this.cerebrovascular = cerebrovascular;
		return this;
	}

	public InsuranceReportTestBuilder withHeartDisease(CoverageStatus heartDisease) {
		this.heartDisease = heartDisease;
		return this;
	}

	public InsuranceReportTestBuilder withSurgery(CoverageStatus surgery) {
		this.surgery = surgery;
		return this;
	}

	public InsuranceReportTestBuilder withDiseaseSurgery(CoverageStatus diseaseSurgery) {
		this.diseaseSurgery = diseaseSurgery;
		return this;
	}

	public InsuranceReportTestBuilder withDiseaseTypeSurgery(CoverageStatus diseaseTypeSurgery) {
		this.diseaseTypeSurgery = diseaseTypeSurgery;
		return this;
	}

	public InsuranceReportTestBuilder withInjurySurgery(CoverageStatus injurySurgery) {
		this.injurySurgery = injurySurgery;
		return this;
	}

	public InsuranceReportTestBuilder withInjuryTypeSurgery(CoverageStatus injuryTypeSurgery) {
		this.injuryTypeSurgery = injuryTypeSurgery;
		return this;
	}

	public InsuranceReportTestBuilder withDailyHospitalization(CoverageStatus dailyHospitalization) {
		this.dailyHospitalization = dailyHospitalization;
		return this;
	}

	public InsuranceReportTestBuilder withDiseaseDailyHospitalization(CoverageStatus diseaseDailyHospitalization) {
		this.diseaseDailyHospitalization = diseaseDailyHospitalization;
		return this;
	}

	public InsuranceReportTestBuilder withInjuryDailyHospitalization(CoverageStatus injuryDailyHospitalization) {
		this.injuryDailyHospitalization = injuryDailyHospitalization;
		return this;
	}

	public InsuranceReportTestBuilder withDisability(CoverageStatus disability) {
		this.disability = disability;
		return this;
	}

	public InsuranceReportTestBuilder withDiseaseDisability(CoverageStatus diseaseDisability) {
		this.diseaseDisability = diseaseDisability;
		return this;
	}

	public InsuranceReportTestBuilder withInjuryDisability(CoverageStatus injuryDisability) {
		this.injuryDisability = injuryDisability;
		return this;
	}

	public InsuranceReportTestBuilder withDeath(CoverageStatus death) {
		this.death = death;
		return this;
	}

	public InsuranceReportTestBuilder withDiseaseDeath(CoverageStatus diseaseDeath) {
		this.diseaseDeath = diseaseDeath;
		return this;
	}

	public InsuranceReportTestBuilder withInjuryDeath(CoverageStatus injuryDeath) {
		this.injuryDeath = injuryDeath;
		return this;
	}

	public InsuranceReport build(){
		return InsuranceReport.builder()
			.user(user)
			.product(product)
			.statistic(statistic)
			.reportRationale(reportRationale)
			.majorDisease(majorDisease)
			.cancer(cancer)
			.cerebrovascular(cerebrovascular)
			.heartDisease(heartDisease)
			.surgery(surgery)
			.injurySurgery(injurySurgery)
			.injuryTypeSurgery(injuryTypeSurgery)
			.diseaseSurgery(diseaseSurgery)
			.diseaseTypeSurgery(diseaseTypeSurgery)
			.dailyHospitalization(dailyHospitalization)
			.diseaseDailyHospitalization(diseaseDailyHospitalization)
			.injuryDailyHospitalization(injuryDailyHospitalization)
			.disability(disability)
			.diseaseDisability(diseaseDisability)
			.injuryDisability(injuryDisability)
			.death(death)
			.injuryDeath(injuryDeath)
			.diseaseDeath(diseaseDeath)
			.build();
	}
}
