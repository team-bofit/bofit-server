package org.sopt.bofit.global.messagebroker.sqs.message;

import java.util.UUID;
import org.sopt.bofit.domain.insurancereport.service.dto.request.ReportRationaleCreateCommand;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.global.messagebroker.message.GenerativeAiMessage;

public record CreateReportRationaleMessage(
    PersonalInfo personalInfo,
    UserInfo userInfo,
    UUID reportId,
    int age
) implements GenerativeAiMessage {

    public ReportRationaleCreateCommand toCommand(){
        return new ReportRationaleCreateCommand(personalInfo, userInfo, reportId, age);
    }
}
