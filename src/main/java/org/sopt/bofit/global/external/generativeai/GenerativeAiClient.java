package org.sopt.bofit.global.external.generativeai;

import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.global.external.generativeai.reportrelational.GenerateReportRationaleRequest;

public interface GenerativeAiClient {
    ReportRationale generateReportRelational(GenerateReportRationaleRequest request);
}
