package org.sopt.bofit.domain.insurance.entity.statistic;

import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class InsuranceBenefitStatistic extends InsuranceBenefit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "insurance_statistic_id")
	private Long id;

	// 연령대 추가 고려중
}
