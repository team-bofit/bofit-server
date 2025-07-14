package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.entity.constant.UserStatus;
import org.sopt.bofit.global.entity.BaseEntity;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.custom_exception.ForbiddenException;

import java.time.LocalDate;
import java.util.Objects;

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

    public void updateName(String name) {
        this.name = name;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void updateJob(Job job) {
        this.job = job;
    }

    public void updateMarried(boolean married) {
        isMarried = married;
    }

    public void updateDriver(boolean driver) {
        isDriver = driver;
    }

    public void updateHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public void recommendedInsurance(){
        this.isRecommendInsurance = true;
    }

    public void checkIsWriter(User writer, ErrorCode errorCode) {
        if (!this.equals(writer)) {
            throw new ForbiddenException(errorCode);
        }
    }

    public void checkIsWriter(Long writerId, ErrorCode errorCode) {
        if (!this.getId().equals(writerId)) {
            throw new ForbiddenException(errorCode);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User that)) {
            return false;
        }
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
