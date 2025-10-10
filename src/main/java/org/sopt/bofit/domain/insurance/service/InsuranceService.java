package org.sopt.bofit.domain.insurance.service;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurance.dto.response.option.InsuranceOptionsResponse;
import org.sopt.bofit.domain.insurance.dto.response.option.MaturityAgeResponse;
import org.sopt.bofit.domain.insurance.dto.response.option.PaymentPeriodResponse;
import org.sopt.bofit.domain.insurance.dto.response.option.RefundTypeResponse;
import org.sopt.bofit.domain.insurance.dto.response.option.RenewableTypeResponse;
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
            Arrays.stream(RenewableType.values()).map(RenewableTypeResponse::from).toList(),
            Arrays.stream(RefundType.values()).map(RefundTypeResponse::from).toList(),
            Arrays.stream(PaymentPeriod.values()).map(PaymentPeriodResponse::from).toList(),
            Arrays.stream(MaturityAge.values()).map(MaturityAgeResponse::from).toList()
        );
    }

}
