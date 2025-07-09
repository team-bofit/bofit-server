package org.sopt.bofit.domain.user.service.dto;

import java.time.LocalDate;

import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;


public record UserUpdate(
	String name,
	Gender gender,
	LocalDate birthDate,
	Job job,
	boolean isMarried,
	boolean isDriver,
	boolean hasChild
) {

}
