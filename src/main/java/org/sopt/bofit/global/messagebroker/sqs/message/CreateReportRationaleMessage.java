package org.sopt.bofit.global.messagebroker.sqs.message;

import java.util.UUID;
import org.sopt.bofit.domain.insurancereport.service.dto.request.InsuranceCriteria;
import org.sopt.bofit.domain.insurancereport.service.dto.request.ReportRationaleCreateCommand;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.global.external.generativeai.reportrelational.GenerateReportRationaleRequest;
import org.sopt.bofit.global.messagebroker.message.GenerativeAiMessage;

public record CreateReportRationaleMessage(
    PersonalInfo personalInfo,
    InsuranceCriteria insuranceCriteria,
    UUID reportId,
//    Long insuranceProductId,
    int age
) implements GenerativeAiMessage {

    public ReportRationaleCreateCommand toCommand(){
        return new ReportRationaleCreateCommand(personalInfo, insuranceCriteria, reportId, age);
    }

    public static CreateReportRationaleMessage from(GenerateReportRationaleRequest request){
        return new CreateReportRationaleMessage(
            request.personalInfo(),
            request.insuranceCriteria(),
            request.report().getId(),
            request.age());
    }
}
