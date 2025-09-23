package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.entity.constant.LoginProvider;
import org.sopt.bofit.domain.user.entity.constant.UserNicknameConstant;
import org.sopt.bofit.domain.user.entity.constant.UserStatus;
import org.sopt.bofit.global.entity.BaseEntity;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.customexception.ForbiddenException;

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

    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Embedded
    private PersonalInfo personalInfo;

    @Column(name = "is_recommend_insurance")
    private boolean isRecommendInsurance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    public void updatePersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void updateName(String name) {
        this.personalInfo.updateName(name);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImage = profileImageUrl;
    }

    public void updateGender(Gender gender) {
        this.personalInfo.updateGender(gender);
    }

    public void updateBirthDate(LocalDate birthDate) {
        this.personalInfo.updateBirthDate(birthDate);
    }

    public void updateJob(Job job) {
        this.personalInfo.updateJob(job);
    }

    public void updateMarried(boolean married) {
        this.personalInfo.updateMarried(married);
    }

    public void updateDriver(boolean driver) {
        this.personalInfo.updateDriver(driver);
    }

    public void updateHasChild(boolean hasChild) {
        this.personalInfo.updateHasChild(hasChild);
    }

    public void recommendedInsurance(){
        this.isRecommendInsurance = true;
    }

    public void updateOauthId(String oauthId) {
        this.oauthId = oauthId;
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

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
        this.nickname = UserNicknameConstant.ANONYMOUS_NICKNAME;
        this.profileImage = null;
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
