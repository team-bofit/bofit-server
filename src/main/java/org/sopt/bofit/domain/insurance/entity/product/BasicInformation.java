package org.sopt.bofit.domain.insurance.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicInformation {

	@Column(name = "product_name", length = 50, nullable = false)
	private String name;

	@Column(name = "company", length = 30, nullable = false)
	private String company;

	@Column(name = "product_type", length = 50)
	private String productType;

	@Column(name = "min_enrollment_age", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private int minEnrollmentAge;
	@Column(name = "max_enrollment_age", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private int maxEnrollmentAge;

	@Column(name = "premium", nullable = false,
		columnDefinition = "MEDIUMINT UNSIGNED DEFAULT 0")
	private int premium;

	@Column(name = "maturity_age", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private int	maturityAge;

	@Column(name = "payment_period_years", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private int paymentPeriodYears;

	@Builder
	private BasicInformation(String name, String company, String productType, int minEnrollmentAge, int maxEnrollmentAge,
		int premium, int maturityAge, int paymentPeriodYears) {
		this.name = name;
		this.company = company;
		this.productType = productType;
		this.minEnrollmentAge = minEnrollmentAge;
		this.maxEnrollmentAge = maxEnrollmentAge;
		this.premium = premium;
		this.maturityAge = maturityAge;
		this.paymentPeriodYears = paymentPeriodYears;
	}
}
