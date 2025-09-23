package org.sopt.bofit.domain.insurancereport.fixture;

import java.time.LocalDate;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;

public class UserFixture {

    public static final String USER_NICKNAME = "테스트용 유저";
    public static final String USER_OAUTH_ID = "123456789";
    public static final LoginProvider USER_LOGIN_PROVIDER = LoginProvider.KAKAO;
    public static final String USER_PROFILE_IMAGE = "profile.jpg";


    public static final String PERSONAL_NAME = "테스터";
    public static final Gender PERSONAL_GENDER = Gender.MALE;
    public static final LocalDate PERSONAL_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public static final Job PERSONAL_JOB = Job.STUDENT;
    public static final boolean PERSONAL_IS_MARRIED = false;
    public static final boolean PERSONAL_IS_DRIVER = false;
    public static final boolean PERSONAL_HAS_CHILD = false;


    public static User getUser(){
        return User.builder()
            .nickname(USER_NICKNAME)
            .oauthId(USER_OAUTH_ID)
            .loginProvider(USER_LOGIN_PROVIDER)
            .profileImage(USER_PROFILE_IMAGE)
            .personalInfo(getPersonalInfo())
            .build();
    }

    public static User getUser(PersonalInfo personalInfo){
        return User.builder()
            .nickname(USER_NICKNAME)
            .oauthId(USER_OAUTH_ID)
            .loginProvider(USER_LOGIN_PROVIDER)
            .profileImage(USER_PROFILE_IMAGE)
            .personalInfo(personalInfo)
            .build();
    }

    public static PersonalInfo getPersonalInfo(){
        return PersonalInfo.builder()
            .name(PERSONAL_NAME)
            .gender(PERSONAL_GENDER)
            .birthDate(PERSONAL_BIRTH_DATE)
            .job(PERSONAL_JOB)
            .isDriver(PERSONAL_IS_DRIVER)
            .isMarried(PERSONAL_IS_MARRIED)
            .hasChild(PERSONAL_HAS_CHILD)
            .build();
    }

}
