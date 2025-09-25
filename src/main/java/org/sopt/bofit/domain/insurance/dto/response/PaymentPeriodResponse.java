package org.sopt.bofit.domain.insurance.dto.response;

import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;

public record PaymentPeriodResponse(
    PaymentPeriod paymentPeriod,
    String displayName
) {

    public static PaymentPeriodResponse create(PaymentPeriod paymentPeriod){
        return new PaymentPeriodResponse(paymentPeriod, paymentPeriod.getDisplayName());
    }

}
