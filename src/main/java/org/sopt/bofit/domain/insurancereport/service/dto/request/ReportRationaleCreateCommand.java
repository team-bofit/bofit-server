package org.sopt.bofit.domain.insurancereport.service.dto.request;

import java.util.UUID;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.UserInfo;

public record ReportRationaleCreateCommand (
    PersonalInfo personalInfo,
    UserInfo userInfo,
    UUID reportId,
    int age
){

}
