package org.sopt.bofit.domain.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class BasicInformation {

	@Column(name = "provider", length = 30)
	private String provider;
	@Column(name = "product_type", length = 50)
	private String productType;

	@Column(name = "min_enrollment_age", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private Integer minEnrollmentAge;
	@Column(name = "max_enrollment_age", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private Integer maxEnrollmentAge;

	@Column(name = "premium", nullable = false,
		columnDefinition = "MEDIUMINT UNSIGNED DEFAULT 0")
	private Integer premium;

	@Column(name = "matueity_age", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private Integer	maturityAge;

	@Column(name = "payment_period_years", nullable = false,
		columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
	private Integer paymentPeriodYears;

}
