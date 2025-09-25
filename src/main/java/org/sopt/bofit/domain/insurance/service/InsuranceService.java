package org.sopt.bofit.domain.insurance.service;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurance.dto.response.InsuranceOptionsResponse;
import org.sopt.bofit.domain.insurance.dto.response.MaturityAgeResponse;
import org.sopt.bofit.domain.insurance.dto.response.PaymentPeriodResponse;
import org.sopt.bofit.domain.insurance.dto.response.RefundTypeResponse;
import org.sopt.bofit.domain.insurance.dto.response.RenewableTypeResponse;
import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;
import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;
import org.sopt.bofit.domain.insurance.entity.product.constant.RefundType;
import org.sopt.bofit.domain.insurance.entity.product.constant.RenewableType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    public InsuranceOptionsResponse getOptionInfos(){
        return new InsuranceOptionsResponse(
            Arrays.stream(RenewableType.values()).map(RenewableTypeResponse::create).toList(),
            Arrays.stream(RefundType.values()).map(RefundTypeResponse::create).toList(),
            Arrays.stream(PaymentPeriod.values()).map(PaymentPeriodResponse::create).toList(),
            Arrays.stream(MaturityAge.values()).map(MaturityAgeResponse::create).toList()
        );
    }

}
