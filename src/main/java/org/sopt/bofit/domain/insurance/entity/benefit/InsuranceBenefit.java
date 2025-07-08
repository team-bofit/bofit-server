package org.sopt.bofit.domain.insurance.entity.benefit;

import org.sopt.bofit.global.entity.BaseEntity;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class InsuranceBenefit extends BaseEntity {

	@Embedded
	private MajorDisease majorDisease;

	@Embedded
	private Surgery surgery;

	@Embedded
	private DailyHospitalization dailyHospitalization;

	@Embedded
	private Disability disability;

	@Embedded
	private Death death;

}
