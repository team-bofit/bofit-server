package org.sopt.bofit.domain.insurance.dto.response;

import lombok.Builder;
import org.sopt.bofit.domain.insurance.dto.response.option.PaymentPeriodResponse;
import org.sopt.bofit.domain.insurance.dto.response.option.RefundTypeResponse;
import org.sopt.bofit.domain.insurance.dto.response.option.RenewableTypeResponse;
import org.sopt.bofit.domain.insurance.entity.product.BasicInformation;
import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;
import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;
import org.sopt.bofit.domain.insurance.entity.product.constant.RefundType;
import org.sopt.bofit.domain.insurance.entity.product.constant.RenewableType;

/**
 * @param maturityAge 다른 선택 항목들과 동일하게 MaturityAgeResponse 를 사용하고 싶었지만, 새롭게 만기 나이를 선택할 수 있도록
 *                    Enum 으로 변경되면서 기존에 사용하던 응답값을 그대로 사용해야하는 클라이언트들의 리소스가 늘어난다고 판단하여
 *                    MaturityAge Enum을 도입하기 이전과 동일한 형태로 int 값을 리턴하고 있음.
 */
public record BasicInformationResponse (
    String name,
    String company,
    String productType,
    int minEnrollmentAge,
    int maxEnrollmentAge,
    int premium,
    int maturityAge,
    PaymentPeriodResponse paymentPeriod,
    RenewableTypeResponse renewableType,
    RefundTypeResponse refundType
){

    public static BasicInformationResponse from(BasicInformation basicInformation) {
        return BasicInformationResponse.builder()
            .name(basicInformation.getName())
            .company(basicInformation.getCompany())
            .productType(basicInformation.getProductType())
            .minEnrollmentAge(basicInformation.getMinEnrollmentAge())
            .maxEnrollmentAge(basicInformation.getMaxEnrollmentAge())
            .premium(basicInformation.getPremium())
            .maturityAge(basicInformation.getMaturityAge())
            .paymentPeriod(basicInformation.getPaymentPeriod())
            .renewableType(basicInformation.getRenewableType())
            .refundType(basicInformation.getRefundType())
            .build();
    }

    @Builder
    private BasicInformationResponse(String name, String company, String productType,
        int minEnrollmentAge, int maxEnrollmentAge, int premium,
        MaturityAge maturityAge, PaymentPeriod paymentPeriod, RenewableType renewableType, RefundType refundType
    ) {
        this(
            name,
            company,
            productType,
            minEnrollmentAge,
            maxEnrollmentAge,
            premium,
            maturityAge.getAge(),
            PaymentPeriodResponse.from(paymentPeriod),
            RenewableTypeResponse.from(renewableType),
            RefundTypeResponse.from(refundType)
        );
    }
}
