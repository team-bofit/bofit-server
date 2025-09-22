package org.sopt.bofit.domain.user.service.dto.request;

import java.util.List;
import java.util.Map;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;


public record UserInfoCommand (
    int minPrice,
    int maxPrice,
    List<DiagnosedDisease> diseaseHistory,
    List<DiagnosedDisease> familyHistory,
    Map<CoveragePreference, Integer> coveragePreferences
){

    public UserInfo createUserInfo(User user){
        return UserInfo.builder()
            .maxPrice(maxPrice)
            .minPrice(minPrice)
            .diseaseHistory(diseaseHistory)
            .familyHistory(familyHistory)
            .coveragePreferences(coveragePreferences)
            .build();
    }

}
