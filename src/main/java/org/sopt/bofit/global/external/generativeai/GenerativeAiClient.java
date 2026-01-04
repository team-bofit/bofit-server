package org.sopt.bofit.global.external.generativeai;

import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.global.external.generativeai.reportrelational.GenerateReportRelationalRequest;

public interface GenerativeAiClient {
    ReportRationale generateReportRelational(GenerateReportRelationalRequest request);
}
