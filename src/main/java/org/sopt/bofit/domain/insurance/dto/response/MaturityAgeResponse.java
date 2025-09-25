package org.sopt.bofit.domain.insurance.dto.response;

import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;

public record MaturityAgeResponse(
    MaturityAge maturityAge,
    String displayName
) {

    public static MaturityAgeResponse create(MaturityAge maturityAge){
        return new MaturityAgeResponse(maturityAge, maturityAge.getDisplayName());
    }

}
