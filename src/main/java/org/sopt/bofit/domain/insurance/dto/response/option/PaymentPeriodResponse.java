package org.sopt.bofit.domain.insurance.dto.response.option;

import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;

public record PaymentPeriodResponse(
    PaymentPeriod paymentPeriod,
    String displayName
) {

    public static PaymentPeriodResponse from(PaymentPeriod paymentPeriod){
        return new PaymentPeriodResponse(paymentPeriod, paymentPeriod.getDisplayName());
    }

}
