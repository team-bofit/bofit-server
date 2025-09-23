package org.sopt.bofit.domain.user.service.dto.request;

import java.time.LocalDate;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;


public record PersonalInfoCommand(
	String name,
	Gender gender,
	LocalDate birthDate,
	Job job,
	boolean isMarried,
	boolean isDriver,
	boolean hasChild
) {

}
