package org.sopt.bofit.global.messagebroker.sqs.message;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.global.external.generativeai.reportrelational.GenerateReportRelationalRequest;
import org.sopt.bofit.global.messagebroker.message.GenerativeAiMessage;

public record CreateReportRationaleMessage(
    PersonalInfo personalInfo,
    UserInfo userInfo,
    InsuranceReport report,
    int age
) implements GenerativeAiMessage {

    public GenerateReportRelationalRequest toRequest(){
        return GenerateReportRelationalRequest.create(
            this.personalInfo, this.userInfo, this.report, this.age);
    }

}
