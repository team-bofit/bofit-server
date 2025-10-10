package org.sopt.bofit.domain.insurance.dto.response.option;

import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;

public record MaturityAgeResponse(
    MaturityAge maturityAge,
    String displayName
) {

    public static MaturityAgeResponse from(MaturityAge maturityAge){
        return new MaturityAgeResponse(maturityAge, maturityAge.getDisplayName());
    }

}
