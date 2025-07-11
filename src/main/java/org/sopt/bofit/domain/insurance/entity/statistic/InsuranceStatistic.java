package org.sopt.bofit.domain.insurance.entity.statistic;

import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "insurance_statistic")
public class InsuranceStatistic extends InsuranceBenefit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "insurance_statistic_id")
	private Long id;

	@Column(unique = true)
	@Enumerated(EnumType.STRING)
	private StatisticRange statisticRange;
}
