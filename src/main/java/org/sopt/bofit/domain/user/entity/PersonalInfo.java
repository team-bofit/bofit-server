package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalInfo {

    private String name;

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


    @Builder
    private PersonalInfo(String name, Gender gender, LocalDate birthDate, Job job,
        boolean isMarried, boolean isDriver, boolean hasChild) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.job = job;
        this.isMarried = isMarried;
        this.isDriver = isDriver;
        this.hasChild = hasChild;
    }

    protected void updateName(String name) {
        this.name = name;
    }

    protected void updateGender(Gender gender) {
        this.gender = gender;
    }

    protected void updateBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    protected void updateJob(Job job) {
        this.job = job;
    }

    protected void updateMarried(boolean married) {
        isMarried = married;
    }

    protected void updateDriver(boolean driver) {
        isDriver = driver;
    }

    protected void updateHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
