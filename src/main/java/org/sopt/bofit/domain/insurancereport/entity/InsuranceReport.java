package org.sopt.bofit.domain.insurancereport.entity;

import java.util.UUID;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "insurance_report")
@Getter
public class InsuranceReport extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "insurance_report_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "insurance_product_id")
	private InsuranceProduct product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "insurance_statistic_id")
	private InsuranceStatistic statistic;

	/**
	 * 큰 병
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "major_disease", nullable = false)
	private CoverageStatus majorDisease;

	@Enumerated(EnumType.STRING)
	@Column(name = "cancer", nullable = false)
	private CoverageStatus cancer;

	@Enumerated(EnumType.STRING)
	@Column(name = "cerebrovascular", nullable = false)
	private CoverageStatus cerebrovascular;

	@Enumerated(EnumType.STRING)
	@Column(name = "heart_disease", nullable = false)
	private CoverageStatus heartDisease;

	/**
	 * 수술비
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "surgery", nullable = false)
	private CoverageStatus surgery;

	@Enumerated(EnumType.STRING)
	@Column(name = "disesase_surgery", nullable = false)
	private CoverageStatus diseaseSurgery;

	@Enumerated(EnumType.STRING)
	@Column(name = "disesase_type_surgery", nullable = false)
	private CoverageStatus diseaseTypeSurgery;

	@Enumerated(EnumType.STRING)
	@Column(name = "injury_surgery", nullable = false)
	private CoverageStatus injurySurgery;

	@Enumerated(EnumType.STRING)
	@Column(name = "injury_type_surgery", nullable = false)
	private CoverageStatus injuryTypeSurgery;

	/**
	 * 입원비
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "daily_hospitalization", nullable = false)
	private CoverageStatus dailyHospitalization;

	@Enumerated(EnumType.STRING)
	@Column(name = "disease_daily_hospitalization", nullable = false)
	private CoverageStatus diseaseDailyHospitalization;

	@Enumerated(EnumType.STRING)
	@Column(name = "injury_daily_hospitalization", nullable = false)
	private CoverageStatus injuryDailyHospitalization;

	/**
	 * 장해
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "disablitiy", nullable = false)
	private CoverageStatus disability;

	@Enumerated(EnumType.STRING)
	@Column(name = "disease_disablitiy", nullable = false)
	private CoverageStatus diseaseDisability;

	@Enumerated(EnumType.STRING)
	@Column(name = "injury_disablitiy", nullable = false)
	private CoverageStatus injuryDisability;

	/**
	 * 사망
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "death", nullable = false)
	private CoverageStatus death;

	@Enumerated(EnumType.STRING)
	@Column(name = "disease_death", nullable = false)
	private CoverageStatus diseaseDeath;

	@Enumerated(EnumType.STRING)
	@Column(name = "injury_death", nullable = false)
	private CoverageStatus injuryDeath;

	@Builder
	private InsuranceReport(
		InsuranceProduct product,
		InsuranceStatistic statistic,
		CoverageStatus majorDisease,
		CoverageStatus cancer,
		CoverageStatus cerebrovascular,
		CoverageStatus heartDisease,
		CoverageStatus surgery,
		CoverageStatus diseaseSurgery,
		CoverageStatus diseaseTypeSurgery,
		CoverageStatus injurySurgery,
		CoverageStatus injuryTypeSurgery,
		CoverageStatus dailyHospitalization,
		CoverageStatus diseaseDailyHospitalization,
		CoverageStatus injuryDailyHospitalization,
		CoverageStatus disability,
		CoverageStatus diseaseDisability,
		CoverageStatus injuryDisability,
		CoverageStatus death,
		CoverageStatus diseaseDeath,
		CoverageStatus injuryDeath
	) {
		this.product = product;
		this.statistic = statistic;

		this.majorDisease = majorDisease;
		this.cancer = cancer;
		this.cerebrovascular = cerebrovascular;
		this.heartDisease = heartDisease;

		this.surgery = surgery;

		this.diseaseSurgery = diseaseSurgery;
		this.diseaseTypeSurgery = diseaseTypeSurgery;

		this.injurySurgery = injurySurgery;
		this.injuryTypeSurgery = injuryTypeSurgery;

		this.dailyHospitalization = dailyHospitalization;
		this.diseaseDailyHospitalization = diseaseDailyHospitalization;
		this.injuryDailyHospitalization = injuryDailyHospitalization;

		this.disability = disability;
		this.diseaseDisability = diseaseDisability;
		this.injuryDisability = injuryDisability;

		this.death = death;
		this.diseaseDeath = diseaseDeath;
		this.injuryDeath = injuryDeath;
	}
}
