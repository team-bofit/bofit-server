package org.sopt.bofit.domain.insurance.entity.product;

import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
