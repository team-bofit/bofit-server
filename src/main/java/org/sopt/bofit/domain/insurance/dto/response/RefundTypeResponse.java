package org.sopt.bofit.domain.insurance.dto.response;

import org.sopt.bofit.domain.insurance.entity.product.constant.RefundType;

public record RefundTypeResponse(
    RefundType refundType,
    String displayName
) {

    public static RefundTypeResponse create(RefundType refundType) {
        return new RefundTypeResponse(refundType, refundType.getDisplayName());
    }

}
