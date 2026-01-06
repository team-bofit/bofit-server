package org.sopt.bofit.domain.insurancereport.service.dto.request;

import java.util.List;
import java.util.Map;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;

/**
 * TODO 보험 관련 도메인에서 UserInfo 대신 해당 Dto 를 사용하도록 리팩토링 하기, PersonalInfo 등도 VO 를 사용하고 있어서 당장은 문제 없지만 DTO 로 wrapping 해서 사용하도록 리팩토링 하기
 */
public record InsuranceCriteria(
    int minPrice,
    int maxPrice,
    List<DiagnosedDisease> diseaseHistory,
    List<DiagnosedDisease> familyHistory,
    Map<CoveragePreference, Integer> coveragePreferences
) {
   public static InsuranceCriteria from(UserInfo userInfo){
       return new InsuranceCriteria(
           userInfo.getMinPrice(),
           userInfo.getMaxPrice(),
           userInfo.getDiseaseHistory(),
           userInfo.getFamilyHistory(),
           userInfo.getCoveragePreferences()
       );
   }
}
