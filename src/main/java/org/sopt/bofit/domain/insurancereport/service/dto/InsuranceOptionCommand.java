package org.sopt.bofit.domain.insurancereport.service.dto;

import java.util.Optional;
import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;
import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;
import org.sopt.bofit.domain.insurance.entity.product.constant.RefundType;
import org.sopt.bofit.domain.insurance.entity.product.constant.RenewableType;

public record InsuranceOptionCommand (
    Optional<RenewableType> renewableType,
    Optional<RefundType> refundType,
    Optional<PaymentPeriod> paymentPeriod,
    Optional<MaturityAge> maturityAge
){

}
