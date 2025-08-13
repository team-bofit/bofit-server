package org.sopt.bofit.domain.insurance.entity.product;

import org.sopt.bofit.domain.insurance.entity.benefit.DailyHospitalization;
import org.sopt.bofit.domain.insurance.entity.benefit.Death;
import org.sopt.bofit.domain.insurance.entity.benefit.Disability;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.benefit.MajorDisease;
import org.sopt.bofit.domain.insurance.entity.benefit.Surgery;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "insurance_product")
public class InsuranceProduct extends InsuranceBenefit {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "insurance_product_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private InsuranceStatus status;

	@Embedded
	private BasicInformation basicInformation;

	@Embedded
	private ExtraInformation extraInformation;

	public static InsuranceProduct create(
		InsuranceStatus status,
		BasicInformation basicInformation,
		ExtraInformation extraInformation,
		MajorDisease majorDisease,
		Surgery surgery,
		DailyHospitalization dailyHospitalization,
		Disability disability,
		Death death
		){
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

	@Builder
	private InsuranceProduct(
		InsuranceStatus status,
		BasicInformation basicInformation,
		ExtraInformation extraInformation,
		MajorDisease majorDisease,
		Surgery surgery,
		DailyHospitalization dailyHospitalization,
		Disability disability,
		Death death
	) {
		this.status = status;
		this.basicInformation = basicInformation;
		this.extraInformation = extraInformation;
		this.majorDisease = majorDisease;
		this.surgery = surgery;
		this.dailyHospitalization = dailyHospitalization;
		this.disability = disability;
		this.death = death;
	}

}
