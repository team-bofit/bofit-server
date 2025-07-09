package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.entity.constant.UserStatus;
import org.sopt.bofit.global.entity.BaseEntity;

import java.time.LocalDate;

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

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Job job;

    @Column(name = "is_married")
    private boolean isMarried;

    @Column(name = "is_driver")
    private boolean isDriver;

    @Column(name = "has_child")
    private boolean hasChild;

    @Column(name = "is_recommend_insurance")
    private boolean isRecommendInsurance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;


}
