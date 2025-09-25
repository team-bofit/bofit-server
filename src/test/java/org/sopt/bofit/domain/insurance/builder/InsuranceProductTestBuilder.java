package org.sopt.bofit.domain.insurance.builder;

import org.sopt.bofit.domain.insurance.entity.product.BasicInformation;
import org.sopt.bofit.domain.insurance.entity.product.ExtraInformation;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;
import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;
import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;
import org.sopt.bofit.domain.insurance.entity.product.constant.RefundType;
import org.sopt.bofit.domain.insurance.entity.product.constant.RenewableType;

public class InsuranceProductTestBuilder extends
	InsuranceBenefitTestBuilder<InsuranceProductTestBuilder, InsuranceProduct> {

	private String name = "테스트용 상품명";
	private String company = "테스트용 회사명";
	private int premium = 10000;
	private MaturityAge maturityAge = MaturityAge.OLD_100;
	private int minEnrollmentAge = 0;
	private int maxEnrollmentAge = 100;
	private PaymentPeriod paymentPeriodYears = PaymentPeriod.YEAR_20;
    private RenewableType renewableType = RenewableType.RENEWABLE;
    private RefundType refundType = RefundType.FULL_RETURN;

	private String remarks = "테스트용 비고";
	private String externalUri = "테스용 uri";
	private String source = "테스용 source";

	private InsuranceStatus status = InsuranceStatus.SELLING;

	public InsuranceProductTestBuilder withName(String name) { this.name = name; return this; }
	public InsuranceProductTestBuilder withCompany(String company) { this.company = company; return this; }
	public InsuranceProductTestBuilder withPremium(int premium) { this.premium = premium; return this; }
	public InsuranceProductTestBuilder withMaturityAge(MaturityAge age) { this.maturityAge = age; return this; }
	public InsuranceProductTestBuilder withMinEnrollmentAge(int age) { this.minEnrollmentAge = age; return this; }
	public InsuranceProductTestBuilder withMaxEnrollmentAge(int age) { this.maxEnrollmentAge = age; return this; }
	public InsuranceProductTestBuilder withPaymentPeriodYears(PaymentPeriod years) { this.paymentPeriodYears = years; return this; }
	public InsuranceProductTestBuilder withRemarks(String remarks) { this.remarks = remarks; return this; }

	public InsuranceProductTestBuilder withStatus(InsuranceStatus status) { this.status = status; return this; }

	@Override
	public InsuranceProduct build() {

		BasicInformation basicInformation = BasicInformation.builder()
			.name(name)
			.company(company)
			.premium(premium)
			.maturityAge(maturityAge)
			.minEnrollmentAge(minEnrollmentAge)
			.maxEnrollmentAge(maxEnrollmentAge)
            .paymentPeriod(paymentPeriodYears)
            .refundType(refundType)
            .renewableType(renewableType)
			.build();

		ExtraInformation extraInformation = ExtraInformation.builder()
			.remarks(remarks)
			.externalUri(externalUri)
			.source(source)
			.build();

		InsuranceProduct product = InsuranceProduct.builder()
			.status(status)
			.basicInformation(basicInformation)
			.extraInformation(extraInformation)
			.build();

		applyCommonFields(product);

		return product;
	}
}
