package org.sopt.bofit.domain.insurancereport.service.dto.request;

import java.util.UUID;
import org.sopt.bofit.domain.user.entity.PersonalInfo;

public record ReportRationaleCreateCommand (
    PersonalInfo personalInfo,
    InsuranceCriteria insuranceCriteria,
    UUID reportId,
    Long insuranceProductId,
    int age
){

}
