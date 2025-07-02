package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.global.entity.BaseEntity;

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

    @Column(unique = true, nullable = false)
    private Long kakaoId;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_day")
    private String birthDay;

    @Column(name = "birth_year")
    private String birthYear;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String job;

    @Column(name = "is_married")
    private boolean isMarried;

    @Column(name = "is_driver")
    private boolean isDriver;

    @Column(name = "has_child")
    private boolean hasChild;

    @Column(name = "is_registered")
    private boolean isRegistered;

    private String status;



}
