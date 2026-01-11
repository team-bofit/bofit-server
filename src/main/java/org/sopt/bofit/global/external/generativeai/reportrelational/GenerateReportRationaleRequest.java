package org.sopt.bofit.global.external.generativeai.reportrelational;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.service.dto.request.InsuranceCriteria;
import org.sopt.bofit.domain.user.entity.PersonalInfo;

public record GenerateReportRationaleRequest(
    PersonalInfo personalInfo,
    InsuranceCriteria insuranceCriteria,
    InsuranceReport report,
    InsuranceProduct product,
    int age
) {
    public static GenerateReportRationaleRequest create(
        PersonalInfo personalInfo,
        InsuranceCriteria insuranceCriteria,
        InsuranceReport report,
        InsuranceProduct product,
        int age
    ){
      return new GenerateReportRationaleRequest(personalInfo, insuranceCriteria, report, product, age);
    }

}
