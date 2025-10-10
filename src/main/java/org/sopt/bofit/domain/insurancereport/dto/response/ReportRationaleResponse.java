package org.sopt.bofit.domain.insurancereport.dto.response;

import java.util.List;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;

public record ReportRationaleResponse(
    List<String> reasons,
    List<String> keywordChips
) {

    public static ReportRationaleResponse from(ReportRationale reportRationale){
        return new ReportRationaleResponse(reportRationale.getReasons(), reportRationale.getKeywordChips());
    }

}
