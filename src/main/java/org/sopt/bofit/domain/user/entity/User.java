package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.entity.constant.UserStatus;
import org.sopt.bofit.global.entity.BaseEntity;

import java.time.MonthDay;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_provider", nullable = false)
    private LoginProvider loginProvider;

    @Column(unique = true, nullable = false, name = "oauth_id")
    private String oauthId;

    private String name;

    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_day")
    private MonthDay birthDay;

    @Column(name = "birth_year")
    private int birthYear;

    @Enumerated(EnumType.STRING)
    private Job job;

    @Column(name = "is_married")
    private boolean isMarried;

    @Column(name = "is_driver")
    private boolean isDriver;

    @Column(name = "has_child")
    private boolean hasChild;

    @Column(name = "is_registered")
    private boolean isRegistered;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    public static User create(
            LoginProvider loginProvider,
            String oauthId,
            String name,
            String nickname,
            String profileImage,
            Gender gender,
            int birthYear,
            MonthDay birthDay
    ) {
        return User.builder()
                .loginProvider(loginProvider)
                .oauthId(oauthId)
                .name(name)
                .nickname(nickname)
                .profileImage(profileImage)
                .gender(gender)
                .birthYear(birthYear)
                .birthDay(birthDay)
                .isRegistered(false)
                .status(UserStatus.ACTIVE)
                .build();
    }

}
