package org.sopt.bofit.domain.insurance.dto.response;

import java.util.List;

public record InsuranceOptionsResponse(
    List<RenewableTypeResponse> renewableTypes,
    List<RefundTypeResponse> refundTypes,
    List<PaymentPeriodResponse> paymentPeriods,
    List<MaturityAgeResponse> maturityAges
) {

}
