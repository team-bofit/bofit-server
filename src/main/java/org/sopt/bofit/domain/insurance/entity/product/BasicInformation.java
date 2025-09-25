package org.sopt.bofit.domain.insurance.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;
import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;
import org.sopt.bofit.domain.insurance.entity.product.constant.RefundType;
import org.sopt.bofit.domain.insurance.entity.product.constant.RenewableType;
import org.sopt.bofit.domain.insurance.entity.product.converter.MaturityAgeConverter;
import org.sopt.bofit.domain.insurance.entity.product.converter.PaymentPeriodConverter;

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
    @Convert(converter = MaturityAgeConverter.class)
	private MaturityAge maturityAge;

    @Column(name = "payment_period_years", nullable = false,
        columnDefinition = "TINYINT UNSIGNED DEFAULT 0")
    @Convert(converter = PaymentPeriodConverter.class)
    private PaymentPeriod paymentPeriod;

    @Enumerated(EnumType.STRING)
    private RenewableType renewableType;

    @Enumerated(EnumType.STRING)
    private RefundType refundType;

	@Builder
    private BasicInformation(String name, RefundType refundType, RenewableType renewableType,
        PaymentPeriod paymentPeriod, int premium, MaturityAge maturityAge, int maxEnrollmentAge,
        int minEnrollmentAge, String company, String productType) {
        this.name = name;
        this.refundType = refundType;
        this.renewableType = renewableType;
        this.paymentPeriod = paymentPeriod;
        this.premium = premium;
        this.maturityAge = maturityAge;
        this.maxEnrollmentAge = maxEnrollmentAge;
        this.minEnrollmentAge = minEnrollmentAge;
        this.company = company;
        this.productType = productType;
    }
}
