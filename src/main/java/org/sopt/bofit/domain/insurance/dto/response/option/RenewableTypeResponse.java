package org.sopt.bofit.domain.insurance.dto.response.option;

import org.sopt.bofit.domain.insurance.entity.product.constant.RenewableType;

public record RenewableTypeResponse(
    RenewableType renewableType,
    String displayName
) {

    public static RenewableTypeResponse from(RenewableType renewableType){
        return new RenewableTypeResponse(renewableType, renewableType.getDisplayName());
    }

}
