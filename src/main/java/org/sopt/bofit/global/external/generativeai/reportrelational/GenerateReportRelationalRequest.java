package org.sopt.bofit.global.external.generativeai.reportrelational;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.UserInfo;

public record GenerateReportRelationalRequest(
    PersonalInfo personalInfo,
    UserInfo userInfo,
    InsuranceReport report,
    int age
) {
    public static GenerateReportRelationalRequest create(
        PersonalInfo personalInfo,
        UserInfo userInfo,
        InsuranceReport report,
        int age
    ){
      return new GenerateReportRelationalRequest(personalInfo, userInfo, report, age);
    }

}
