package org.sopt.bofit.domain.insurancereport.fixture;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.service.dto.request.PersonalInfoCommand;
import org.sopt.bofit.domain.user.service.dto.request.UserInfoCommand;

public class UserInfoFixture {

    public static PersonalInfoCommand userInfoUpdateCommand(){
        return new PersonalInfoCommand(
            "테스트",
            Gender.FEMALE,
            LocalDate.of(2000, 01, 01),
            Job.STUDENT,
            true,
            true,
            true
        );
    }

    public static UserInfoCommand userInfoCommand(
        int minPrice,
        int maxPrice,
        List<DiagnosedDisease> diseaseHistory,
        List<DiagnosedDisease> familyHistory,
        Map<CoveragePreference, Integer> coveragePreferences){
        return new UserInfoCommand(
            minPrice,
            maxPrice,
            diseaseHistory,
            familyHistory,
            coveragePreferences
        );
    }
}
