package org.sopt.bofit.domain.insurance.entity;

import org.sopt.bofit.domain.insurance.entity.enums.InsuranceStatus;
import org.sopt.bofit.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Insurance extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "insurance_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "insurance_status")
	private InsuranceStatus status;

	@Embedded
	private BasicInformation basicInformation;

	@Embedded
	private MajorDiagnosisBenefit majorDiagnosisBenefit;

	@Embedded
	private MajorSurgeryBenefit majorSurgeryBenefit;

	@Embedded
	private DiseaseSurgeryBenefit diseaseSurgerybenefit;

	@Embedded
	private InjurySurgeryBenefit injurySurgeryBenefit;

	@Embedded
	private DailyHospitalization dailyHospitalization;

	@Embedded
	private Disability disability;

	@Embedded
	private DeathBenefit deathBenefit;

	@Embedded
	private ExtraInformation extraInformation;
}
